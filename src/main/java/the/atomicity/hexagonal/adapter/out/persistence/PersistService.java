package the.atomicity.hexagonal.adapter.out.persistence;

import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.Mode;

import java.math.BigInteger;

public interface PersistService {

    void init(String source, boolean clear, Mode mode);

    BigInteger process(final GenericMedia genericMedia);

    void findDuplicate(String source);

    void postLoad();
}
