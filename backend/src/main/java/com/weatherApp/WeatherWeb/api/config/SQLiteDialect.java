package com.weatherApp.WeatherWeb.api.Config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;

public class SQLiteDialect extends H2Dialect {
    public SQLiteDialect() {
        super();
    }
}