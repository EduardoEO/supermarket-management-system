@echo off
setlocal
cd /d "%~dp0"
echo ==========================================
echo  Compilando y generando AdminSupermercado.exe
echo ==========================================

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0build_exe.ps1"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Proceso completado correctamente.
) else (
    echo.
    echo Ha ocurrido un error al generar el ejecutable.
)
pause
