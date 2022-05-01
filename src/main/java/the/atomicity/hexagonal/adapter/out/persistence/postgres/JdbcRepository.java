package the.atomicity.hexagonal.adapter.out.persistence.postgres;

import the.atomicity.hexagonal.adapter.out.persistence.Repository;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.math.BigInteger;
import java.util.List;

public class JdbcRepository implements Repository {

    final JdbcMediaRepository jdbcVideosRepository;
    final JdbcMediaRepository jdbcPicturesRepository;

    public JdbcRepository() {
        this.jdbcVideosRepository = new JdbcVideosRepository();
        this.jdbcPicturesRepository = new JdbcPicturesRepository();
    }

    @Override
    public void init(final String source, final boolean clear) {
        this.jdbcPicturesRepository.init(source, clear);
        this.jdbcVideosRepository.init(source, clear);
    }

    public void delete(final String source, final String absolutePath) {
        this.jdbcPicturesRepository.delete(source, absolutePath);
        this.jdbcVideosRepository.delete(source, absolutePath);
    }

    @Override
    public List<String[]> findDuplicate(final String source) {
        final List<String[]> duplicate = this.jdbcPicturesRepository.findDuplicate(source);
        duplicate.addAll(this.jdbcVideosRepository.findDuplicate(source));
        return duplicate;
    }

    @Override
    public void postSave() {
        // no op
    }

    public BigInteger check(final GenericMedia media) {
        return this.getRepository(media).check(media);
    }

    public BigInteger save(final GenericMedia media) {
        return this.getRepository(media).save(media);
    }

    private JdbcMediaRepository getRepository(final GenericMedia media) {
        switch (media.getType()) {
            case PICTURE:
                return this.jdbcPicturesRepository;
            case VIDEO:
                return this.jdbcVideosRepository;
            default:
                throw new IllegalArgumentException("No repository");
        }
    }
}
