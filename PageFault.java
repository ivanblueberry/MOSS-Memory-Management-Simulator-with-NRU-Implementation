import java.util.*;

public class PageFault {

  /**
   * The page replacement algorithm for the memory management simulator.
   * This method gets called whenever a page needs to be replaced.
   * <p>
   * The page replacement algorithm implemented here is the NRU (Not Recently Used) algorithm. 
   * NRU classifies pages into four categories based on the values of the reference bit (R) and 
   * the modification bit (M):
   * <ul>
   *   <li>Class 0: R = 0, M = 0 (not recently used and not modified, highest priority for replacement).</li>
   *   <li>Class 1: R = 0, M = 1 (not recently used but modified).</li>
   *   <li>Class 2: R = 1, M = 0 (recently used but not modified).</li>
   *   <li>Class 3: R = 1, M = 1 (recently used and modified, lowest priority for replacement).</li>
   * </ul>
   * The algorithm selects a page for replacement by prioritizing the lowest class number (0 being the highest priority).
   * If there are multiple pages in the same class, the first one found is replaced.
   * <p>
   * This method modifies the memory vector to reflect the changes and updates the graphical interface to
   * show the replacement process.
   *
   * @param mem           A vector containing the pages currently in memory. Each element is an instance of the Page class.
   * @param virtPageNum   The total number of virtual pages in the simulator (set in Kernel.java).
   * @param replacePageNum The virtual page number that caused the page fault and needs to be loaded into memory.
   * @param controlPanel  The graphical interface for the simulator, used to update the display.
   */
  public static void replacePage(Vector mem, int virtPageNum, int replacePageNum, ControlPanel controlPanel) {
    // Lists to classify pages into the four NRU categories
    ArrayList<Integer> class0 = new ArrayList<>();
    ArrayList<Integer> class1 = new ArrayList<>();
    ArrayList<Integer> class2 = new ArrayList<>();
    ArrayList<Integer> class3 = new ArrayList<>();

    // Classify pages based on their R (reference) and M (modification) bits
    for (int i = 0; i < virtPageNum; i++) {
        Page page = (Page) mem.elementAt(i);
        if (page.physical != -1) { // Only consider pages currently in physical memory
            if (page.R == 0 && page.M == 0) {
                class0.add(i); // Class 0: Not recently used, not modified
            } else if (page.R == 0 && page.M == 1) {
                class1.add(i); // Class 1: Not recently used, but modified
            } else if (page.R == 1 && page.M == 0) {
                class2.add(i); // Class 2: Recently used, not modified
            } else {
                class3.add(i); // Class 3: Recently used and modified
            }
        }
    }

    // Select a page to replace based on NRU priority
    int pageToReplace = -1;
    if (!class0.isEmpty()) {
        pageToReplace = class0.get(0); // Replace a page from Class 0 (highest priority)
    } else if (!class1.isEmpty()) {
        pageToReplace = class1.get(0); // Replace a page from Class 1
    } else if (!class2.isEmpty()) {
        pageToReplace = class2.get(0); // Replace a page from Class 2
    } else if (!class3.isEmpty()) {
        pageToReplace = class3.get(0); // Replace a page from Class 3 (lowest priority)
    }

    // Fallback: If no page was selected (unlikely), replace the first page
    if (pageToReplace == -1) {
        pageToReplace = 0;
    }

    // Perform the page replacement
    Page page = (Page) mem.elementAt(pageToReplace);
    Page nextPage = (Page) mem.elementAt(replacePageNum);

    // Update the graphical interface to reflect the replacement
    controlPanel.removePhysicalPage(pageToReplace);
    nextPage.physical = page.physical;
    controlPanel.addPhysicalPage(nextPage.physical, replacePageNum);

    // Reset the attributes of the replaced page
    page.inMemTime = 0;
    page.lastTouchTime = 0;
    page.R = 0;
    page.M = 0;
    page.physical = -1;
  }
}