package the.atomicity.hexagonal.application.cmd;

import org.apache.commons.cli.*;
import the.atomicity.hexagonal.domain.Mode;
import the.atomicity.hexagonal.application.LauncherService;

public class RunnerFromCli {

    LauncherService launcherService;

    private Options getOptions() {
        final Options options = new Options();
        options.addOption(Option.builder("s")
                .required(true)
                .longOpt("source")
                .desc("label to identify source of images/videos")
                .hasArg()
                .build());
        options.addOption(Option.builder("p")
                .required(true)
                .longOpt("path")
                .desc("space separated list of paths to images/videos")
                .numberOfArgs(10)
                .hasArgs()
                .build());
        options.addOption(Option.builder("m")
                .required(true)
                .longOpt("mode")
                .desc("json or db")
                .hasArg()
                .build());
        options.addOption(Option.builder("t")
                .longOpt("truncate")
                .desc("truncate tables before loading")
                .build());
        return options;
    }

    public void process(final String[] args) {
        if (args.length == 0) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar tagcrawler.jar", this.getOptions());
            return;
        }
        final CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            final CommandLine commandLine = parser.parse(this.getOptions(), args);
            String source = "tmp";
            if (commandLine.hasOption("source")) {
                source = commandLine.getOptionValue("source");
            }
            String[] path = {"."};
            if (commandLine.hasOption("path")) {
                path = commandLine.getOptionValues("path");
            }
            Mode mode = Mode.JSON;
            if (commandLine.hasOption("mode")) {
                mode = Mode.fromString(commandLine.getOptionValue("mode"));
            }
            boolean truncate = false;
            if (commandLine.hasOption("truncate")) {
                // initialise the member variable
                truncate = true;
            }
            //this.launcherService = new LauncherService(source, path, mode);
            //this.launcherService.load(truncate);
        } catch (final ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

}
