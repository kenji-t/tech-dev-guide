import java.util.*;

public class Matrix {
  int rows;
  int cols;
  int mineCount;
  boolean win; 
  boolean lose;
  int openCount;
  HashMap<String, HashMap<String, Integer>> map;

  Matrix(int rows, int cols, int count) {
    this.rows = rows;
    this.cols = cols;
    this.mineCount = count;
    this.win = false;
    this.lose = false;
    this.openCount = 0;
    this.map = makeMap();
  }

  public void show() {
    for (int i=1; i<=this.rows; i++) {
      String row = "";
      for (int j=1; j<=this.cols; j++) {
        if (this.win || this.lose) {
          row += String.valueOf(this.map.get(coordinate(j, i)).get("value"))+" ";
        } else if (this.map.get(coordinate(j, i)).get("selected") == 1) {
          row += String.valueOf(this.map.get(coordinate(j, i)).get("value"))+" ";
        } else {
          row += "- ";
        }
      }
      System.out.println(row);
    }
  }

  public HashMap<String, HashMap<String, Integer>> makeMap() {
    ArrayList<Integer> list = new ArrayList<Integer>();
    HashMap<String, Integer> mineOnlyMap = new HashMap<String, Integer>();
    HashMap<String, HashMap<String, Integer>> mineCountMap = new HashMap<String, HashMap<String, Integer>>();
    String[] mines = new String[this.mineCount];
    int listLocation = 0;
    int mineLocation = 0;

    for (int i=0; i<(this.rows*this.cols)-this.mineCount; i++) { list.add(0); }
    for (int i=0; i<this.mineCount; i++) { list.add(9); }
    Collections.shuffle(list);

    for (int i=0; i<=this.rows+1; i++) {
      for (int j=0; j<=this.cols+1; j++) {
        if (i == 0 || j == 0 || i == this.rows+1 || j == this.cols+1) {
          mineOnlyMap.put(coordinate(j, i), 0);
        } else {
          mineOnlyMap.put(coordinate(j, i), list.get(listLocation));
          if (list.get(listLocation) == 9) {
            mines[mineLocation] = coordinate(j, i);
            mineLocation++;
          }
          listLocation++;
        }
      }
    }

    for (HashMap.Entry<String, Integer> entry : mineOnlyMap.entrySet()) {
      HashMap<String, Integer> cell = new HashMap<String, Integer>();
      cell.put("selected", 0);
      if (entry.getValue() == 9) {
        cell.put("value", 9);
      } else if (entry.getKey().contains("0-")
       || entry.getKey().contains(String.valueOf(this.rows+1)+"-")
       || entry.getKey().contains("-0")
       || entry.getKey().contains("-"+String.valueOf(this.cols+1))) {
        cell.put("value", 0);
      } else {
        cell.put("value", countMine(mineOnlyMap, entry.getKey()));
      }
      mineCountMap.put(entry.getKey(), cell);
    }

    return mineCountMap;
  }

  public int countMine(HashMap<String, Integer> map, String str) {
    int count = 0;
    int y = Integer.parseInt(str.split("-")[0]);
    int x = Integer.parseInt(str.split("-")[1]);
    if (map.get(coordinate(x-1, y-1)) == 9) count++;
    if (map.get(coordinate(x-0, y-1)) == 9) count++;
    if (map.get(coordinate(x+1, y-1)) == 9) count++;
    if (map.get(coordinate(x-1, y-0)) == 9) count++;
    if (map.get(coordinate(x+1, y-0)) == 9) count++;
    if (map.get(coordinate(x-1, y+1)) == 9) count++;
    if (map.get(coordinate(x-0, y+1)) == 9) count++;
    if (map.get(coordinate(x+1, y+1)) == 9) count++;
    return count;
  }

  public void openCell(int x, int y) {
    if (x < 1 || x > this.cols || y < 1 || y > this.rows) {
      return;
    } else if (isOpened(x, y)) {
      return;
    } else if (getCellNumber(x, y) == 0) {
      open(x, y);
      for (int ny = y - 1; ny <= y + 1; ny++) {
        for (int nx = x - 1; nx <= x + 1; nx++) {
          openCell(nx, ny);
        }
      }
    } else if (getCellNumber(x, y) < 9) {
      open(x, y);
      return;
    } else {
      this.lose = true;
      return;
    }
  }

  public boolean isOpened(int x, int y) {
    return this.map.get(coordinate(x, y)).get("selected") == 1;
  }

  public void open(int x, int y) {
    HashMap<String, Integer> map = this.map.get(coordinate(x, y));
    map.put("selected", 1);
    this.map.put(coordinate(x, y), map);
    this.openCount += 1;
  }

  public void checkOpenCount() {
    if (this.openCount == (this.rows*this.cols-this.mineCount)) this.win = true;
  }

  public int getCellNumber(int x, int y) {
    return this.map.get(coordinate(x, y)).get("value");
  }

  public String coordinate(int x, int y) {
    return String.valueOf(y)+"-"+String.valueOf(x);
  }
}
