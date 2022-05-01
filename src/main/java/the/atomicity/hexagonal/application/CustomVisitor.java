package the.atomicity.hexagonal.application;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class CustomVisitor implements FileVisitor {

    final String source;
    final CrawlerService service;

    public CustomVisitor(final String source, final CrawlerService service) {
        this.source = source;
        this.service = service;
    }

    @Override
    public FileVisitResult preVisitDirectory(final Object dir, final BasicFileAttributes attrs) throws IOException {
        this.service.processFolder(this.source, (Path) dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(final Object file, final BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(final Object file, final IOException exc) throws IOException {
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(final Object dir, final IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
