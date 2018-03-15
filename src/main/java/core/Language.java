package core;

import lombok.Getter;

public enum Language {
    RU("ru"),
    UK("uk"),
    DE("de"),
    EN("en"),
    ML("ml");

    @Getter
    private final String languageCode;

    Language(String lang) {
        this.languageCode = lang;
    }
}
