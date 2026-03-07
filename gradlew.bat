@echo off
setlocal
set JAVA_HOME=C:\Program Files\Java\jdk-25
set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
set APP_HOME=%~dp0

if not exist %JAVA_EXE% (
    echo ERROR: Could not find java.exe at %JAVA_EXE%
    exit /b 1
)

%JAVA_EXE% -Xmx512m -jar "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" %*
endlocal
