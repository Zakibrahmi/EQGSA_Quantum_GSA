package org.um.feri.ears.util.random;

import java.util.Random;

public abstract class RandomGenerator extends Random {
    /**
     * Generates {@code byte} values and places them into a user-supplied array.
     *
     * <p>
     * The array is filled with bytes extracted from random integers.
     * This implies that the number of random bytes generated may be larger than
     * the length of the byte array.
     * </p>
     *
     * @param bytes Array in which to put the generated bytes.
     * Cannot be {@code null}.
     * @param start Index at which to start inserting the generated bytes.
     * @param len Number of bytes to insert.
     * @throws IndexOutOfBoundsException if {@code start < 0} or
     * {@code start >= bytes.length}.
     * @throws IndexOutOfBoundsException if {@code len < 0} or
     * {@code len > bytes.length - start}.
     */
    abstract void nextBytes(byte[] bytes,
                            int start,
                            int len);

}