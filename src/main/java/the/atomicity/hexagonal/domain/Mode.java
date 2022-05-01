package the.atomicity.hexagonal.domain;

public enum Mode {

    JSON("json"), POSTGRES("postgres");

    String command;

    Mode(final String command) {
        this.command = command;
    }

    public static Mode fromString(final String mode) {
        for (final Mode modeValue : Mode.values()) {
            if (modeValue.getCommand().equals(mode)) {
                return modeValue;
            }
        }
        throw new IllegalArgumentException("unknown mode mode");
    }

    public String getCommand() {
        return this.command;
    }

    public static boolean isJson(final String arg) {
        return JSON.getCommand().equals(arg);
    }

    public static boolean isJson(final Mode arg) {
        return JSON.equals(arg);
    }
}
