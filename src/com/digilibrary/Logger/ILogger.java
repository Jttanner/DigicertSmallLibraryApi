package com.digilibrary.Logger;

public interface ILogger {
    void LogInfo(String info);
    void LogException(Exception e, String extraInfo);
}
