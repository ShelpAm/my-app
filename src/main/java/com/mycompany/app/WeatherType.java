package com.mycompany.app;

/**
 * WeatherType categorizes OpenWeatherMap weather condition codes:
 * 200–299 → THUNDERSTORM
 * 300–399 → DRIZZLE
 * 500–599 → RAIN
 * 600–699 → SNOW
 * 700–799 → ATMOSPHERE (mist, smoke, etc.)
 * 800 → CLEAR
 * 801–899 → CLOUDS
 * others → UNKNOWN
 */
public enum WeatherType {
    THUNDERSTORM,
    DRIZZLE,
    RAIN,
    SNOW,
    ATMOSPHERE,
    CLEAR,
    CLOUDS,
    UNKNOWN;

    /**
     * Parse a numeric weather code string into a WeatherType.
     * 
     * @param s numeric code from JSON (e.g. "804")
     * @return corresponding WeatherType, or UNKNOWN if unrecognized
     */
    public static WeatherType fromString(String s) {
        try {
            int code = Integer.parseInt(s.trim());
            return fromCode(code);
        } catch (NumberFormatException e) {
            return UNKNOWN;
        }
    }

    /**
     * Parse a numeric weather code into a WeatherType.
     * 
     * @param code integer code from JSON (e.g. 804)
     * @return corresponding WeatherType, or UNKNOWN if out of range
     */
    public static WeatherType fromCode(int code) {
        if (code >= 200 && code < 300) {
            return THUNDERSTORM;
        } else if (code >= 300 && code < 400) {
            return DRIZZLE;
        } else if (code >= 500 && code < 600) {
            return RAIN;
        } else if (code >= 600 && code < 700) {
            return SNOW;
        } else if (code >= 700 && code < 800) {
            return ATMOSPHERE;
        } else if (code == 800) {
            return CLEAR;
        } else if (code > 800 && code < 900) {
            return CLOUDS;
        } else {
            return UNKNOWN;
        }
    }
}
