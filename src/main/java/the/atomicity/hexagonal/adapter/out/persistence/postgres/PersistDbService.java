package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.Mode;
import the.atomicity.hexagonal.adapter.out.persistence.Repository;
import the.atomicity.hexagonal.adapter.out.persistence.PersistService;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class PersistDbService implements PersistService {

    private final Repository repository;

    public PersistDbService() {
        this.repository = new JdbcRepository();
    }

    @Override
    public void init(final String source, final boolean clear, final Mode mode) {
        this.repository.init(source, clear);
    }

    public BigInteger process(final GenericMedia genericMedia) {
        final BigInteger hash = this.repository.check(genericMedia);
        return (hash == null) ? this.repository.save(genericMedia) : hash;
    }

    @Override
    public void findDuplicate(final String source) {
        final Scanner scanner = new Scanner(System.in);
        final List<String[]> duplicates = this.repository.findDuplicate(source);
        for (final String[] entry : duplicates) {
            System.out.println("Remove [0] " + entry[0] + " [1] " + entry[1] + " ?");
            final int toBeRemoved = scanner.nextInt();
            final File file = Paths.get(entry[toBeRemoved]).toFile();
            System.out.println("Removing " + toBeRemoved + " " + file.getAbsolutePath());
            System.out.println("Removed " + file.delete());
            this.repository.delete(source, file.getAbsolutePath());
        }
    }

    @Override
    public void postLoad() {
        repository.postSave();
    }
}
