package the.atomicity.hexagonal.application.port.out;

import the.atomicity.hexagonal.domain.GenericMedia;

import java.math.BigInteger;

public interface ProcessMediaPort {

    BigInteger process(final GenericMedia genericMedia);

}
