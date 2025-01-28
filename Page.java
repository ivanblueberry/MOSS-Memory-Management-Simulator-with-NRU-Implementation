public class Page 
{
  public int id;
  public int physical;
  public byte R;
  public byte M;
  public int inMemTime;
  public int lastTouchTime;
  public long high;
  public long low;
  public int[] segmentAccesses = new int[4]; // Contadores para cada segmento

  public Page( int id, int physical, byte R, byte M, int inMemTime, int lastTouchTime, long high, long low ) 
  {
    this.id = id;
    this.physical = physical;
    this.R = R;
    this.M = M;
    this.inMemTime = inMemTime;
    this.lastTouchTime = lastTouchTime;
    this.high = high;
    this.low = low;
  }

  public int getSegmentIndex(long address) {
    long segmentSize = (high - low + 1) / 4;
    return (int) ((address - low) / segmentSize); // Determinamos el índice del segmento
  }

  public void accessSegment(long address, boolean isWrite) {
    int segmentIndex = getSegmentIndex(address);
    if (isWrite) {
      segmentAccesses[segmentIndex] = 1; // Marca como modificado
    } else if (segmentAccesses[segmentIndex] == 0 ) {
      segmentAccesses[segmentIndex] = 1; // Marca como referenciado si no fue modificado
    }
  }
}
