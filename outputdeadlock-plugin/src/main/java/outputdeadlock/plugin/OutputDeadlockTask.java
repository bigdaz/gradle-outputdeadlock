package outputdeadlock.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.RegularFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GFileUtils;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class OutputDeadlockTask extends DefaultTask {
    @Inject
    protected abstract ProjectLayout getProjectLayout();

    @InputFiles
    public abstract ConfigurableFileCollection getInputFiles();

    @OutputFiles
    public List<RegularFile> getOutputFiles() {
        System.out.println("Getting output files");
        DirectoryProperty buildDirectory = getProjectLayout().getBuildDirectory();
        List<RegularFile> outputFiles = new ArrayList<>();
        for (File inputFile : getInputFiles()) {
            outputFiles.add(buildDirectory.file(inputFile.getName() + ".out").get());
        }
        return outputFiles;
    }

    @TaskAction
    public void execute() {
        Iterator<RegularFile> outputFilesIterator = getOutputFiles().iterator();
        for (File inputFile : getInputFiles()) {
            File outputFile = outputFilesIterator.next().getAsFile();
            System.out.println("Copying '" + inputFile.getName() + " to " + outputFile.getName());
            GFileUtils.copyFile(inputFile, outputFile);
        }
    }
}
