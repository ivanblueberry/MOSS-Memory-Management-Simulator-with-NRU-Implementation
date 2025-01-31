public class Instruction 
{
  public String inst; // Instrucción
  public long addr; // Dirección de la página
  public long addr1; // Primera dirección de la página
  public long addr2; // Segunda dirección de la página

  public Instruction( String inst, long addr ) 
  {
    this.inst = inst;
    this.addr = addr;
  }

  public Instruction( String inst, long addr1, long addr2 ) 
  {
    this.inst = inst;
    this.addr1 = addr1;
    this.addr2 = addr2;
  }

}
