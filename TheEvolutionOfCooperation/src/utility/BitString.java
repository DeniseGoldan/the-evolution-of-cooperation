package utility;

public class BitString {

    private boolean[] bitString;
    private int numberOfBits;

    public BitString(BitString original) {
        numberOfBits = original.getNumberOfBits();
        bitString = original.bitString.clone();
    }

    public BitString(int desiredNumberOfBits) {
        if (desiredNumberOfBits <= 0) {
            throw new AssertionError("The length of the array can not be smaller than 1.");
        }
        bitString = new boolean[desiredNumberOfBits];
        numberOfBits = desiredNumberOfBits;
    }

    public int getNumberOfBits() {
        return numberOfBits;
    }

    public boolean getBit(int bitIndex) {
        if (bitIndex < 0 || bitIndex >= numberOfBits) {
            throw new RuntimeException("The index is out of bounds.");
        }
        return bitString[bitIndex];
    }

    public void setBit(int bitIndex, boolean desiredValue) {
        if (bitIndex < 0 || bitIndex >= numberOfBits) {
            throw new RuntimeException("The index is out of bounds.");
        }
        bitString[bitIndex] = desiredValue;
    }

    public void negateBit(int bitIndex) {
        if (bitIndex < 0 || bitIndex >= numberOfBits) {
            throw new RuntimeException("The index is out of bounds.");
        }
        bitString[bitIndex] = !bitString[bitIndex];
    }
}