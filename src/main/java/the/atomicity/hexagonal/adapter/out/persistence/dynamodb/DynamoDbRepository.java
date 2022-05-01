package the.atomicity.hexagonal.adapter.out.persistence.dynamodb;

import the.atomicity.hexagonal.adapter.out.persistence.Repository;
import the.atomicity.hexagonal.domain.GenericMedia;

import java.math.BigInteger;
import java.util.List;

public class DynamoDbRepository implements Repository {

    @Override
    public void init(String source, boolean clear) {

    }

    @Override
    public BigInteger check(GenericMedia media) {
        return null;
    }

    @Override
    public BigInteger save(GenericMedia media) {
        return null;
    }

    @Override
    public void delete(String source, String absolutePath) {

    }

    @Override
    public List<String[]> findDuplicate(String source) {
        return null;
    }

    @Override
    public void postSave() {

    }
}
