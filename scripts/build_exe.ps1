<#
.SYNOPSIS
    Script de compilación y empaquetado del ejecutable Windows (.exe) para AdminSupermercado.
.DESCRIPTION
    1. Compila las clases Java desde AdminSupermercado/src.
    2. Empaqueta el archivo JAR ejecutable con Main-Class: Main.
    3. Ensambla el archivo AdminSupermercado.exe utilizando el lanzador PE nativo.
#>

$ErrorActionPreference = "Stop"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host " Compilando y generando AdminSupermercado.exe" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

if ($PSScriptRoot) {
    $rootDir = Split-Path -Parent $PSScriptRoot
} elseif (Test-Path (Join-Path (Get-Location) "AdminSupermercado")) {
    $rootDir = (Get-Location).Path
} else {
    $rootDir = (Get-Item ..).FullName
}

$srcDir = Join-Path $rootDir "AdminSupermercado\src"
$outDir = Join-Path $rootDir "AdminSupermercado\out\production\AdminSupermercado"
$stubPath = Join-Path $rootDir "AdminSupermercado\launcher_stub.bin"
$exePath = Join-Path $rootDir "AdminSupermercado.exe"
$tempManifest = Join-Path $rootDir "manifest_temp.mf"
$tempJar = Join-Path $rootDir "temp_app.jar"

# 1. Crear directorio de salida si no existe
if (-not (Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir -Force | Out-Null
}

# 2. Compilar fuentes Java
Write-Host "[1/3] Compilando clases Java con javac..." -ForegroundColor Yellow
$javaFiles = Get-ChildItem -Path $srcDir -Filter "*.java" | ForEach-Object { $_.FullName }
& javac -encoding UTF-8 -d $outDir $javaFiles

if ($LASTEXITCODE -ne 0) {
    Write-Error "Error en la compilación de Java."
    exit 1
}

# 3. Crear archivo JAR ejecutable
Write-Host "[2/3] Empaquetando JAR ejecutable..." -ForegroundColor Yellow
Set-Content -Path $tempManifest -Value "Manifest-Version: 1.0`nMain-Class: Main`n" -NoNewline
& jar cfm $tempJar $tempManifest -C $outDir .

if ($LASTEXITCODE -ne 0) {
    Remove-Item $tempManifest -Force -ErrorAction SilentlyContinue
    Write-Error "Error creando el archivo JAR."
    exit 1
}
Remove-Item $tempManifest -Force -ErrorAction SilentlyContinue

# 4. Ensamblar ejecutable .exe
Write-Host "[3/3] Ensamblando AdminSupermercado.exe..." -ForegroundColor Yellow
if (-not (Test-Path $stubPath)) {
    Write-Error "No se encontró el lanzador base en $stubPath."
    Remove-Item $tempJar -Force -ErrorAction SilentlyContinue
    exit 1
}

$stubBytes = [System.IO.File]::ReadAllBytes($stubPath)
$jarBytes = [System.IO.File]::ReadAllBytes($tempJar)

$totalBytes = New-Object byte[] ($stubBytes.Length + $jarBytes.Length)
[System.Buffer]::BlockCopy($stubBytes, 0, $totalBytes, 0, $stubBytes.Length)
[System.Buffer]::BlockCopy($jarBytes, 0, $totalBytes, $stubBytes.Length, $jarBytes.Length)

[System.IO.File]::WriteAllBytes($exePath, $totalBytes)
Remove-Item $tempJar -Force -ErrorAction SilentlyContinue

Write-Host "==========================================" -ForegroundColor Green
Write-Host " ¡AdminSupermercado.exe generado con éxito!" -ForegroundColor Green
Write-Host " Ubicación: $exePath" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
