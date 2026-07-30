package mission.config;

import mission.pages.SamplePage;

public class IniClass {

    public static SamplePage samplePage;

    public static void initialize() {

        samplePage = new SamplePage();

    }
}