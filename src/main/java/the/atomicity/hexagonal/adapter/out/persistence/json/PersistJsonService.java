package the.atomicity.hexagonal.adapter.out.persistence.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.atomicity.hexagonal.domain.GenericMedia;
import the.atomicity.hexagonal.domain.Mode;
import the.atomicity.hexagonal.adapter.out.persistence.Repository;
import the.atomicity.hexagonal.adapter.out.persistence.PersistService;

import java.math.BigInteger;

@Service
public class PersistJsonService implements PersistService {

    @Autowired
    private Repository repository;

    @Override
    public void init(final String source, final boolean clear, final Mode mode) {
        // no op
    }

    @Override
    public BigInteger process(final GenericMedia genericMedia) {
        return this.repository.save(genericMedia);
    }

    @Override
    public void findDuplicate(final String source) {
        // no op
    }

    @Override
    public void postLoad() {
        this.repository.postSave();
    }
}
