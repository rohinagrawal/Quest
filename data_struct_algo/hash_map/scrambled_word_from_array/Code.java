package data_struct_algo.hash_map.scrambled_word_from_array;

import java.util.HashMap;

public class Code {
    public void unscramble() {
    String[] words = {"baby", "referee", "cat", "dada", "dog", "bird", "ax", "baz"};
    String note1 = "ctay";
    String note2 = "bcanihjsrrrferet";
    String note3 = "tbaykkjlga";
    String note4 = "bbbblkkjbaby";
    String note5 = "dad";
    String note6 = "breadmaking";
    String note7 = "dadaa";
    System.out.println(findScrambledWord(words, note1));
    System.out.println(findScrambledWord(words, note2));
    System.out.println(findScrambledWord(words, note3));
    System.out.println(findScrambledWord(words, note4));
    System.out.println(findScrambledWord(words, note5));
    System.out.println(findScrambledWord(words, note6));
    System.out.println(findScrambledWord(words, note7));
  }
  
  private String findScrambledWord(String[] words, String note){
      HashMap<Character, Integer> noteCharCount = new HashMap<>();
      for (int i = 0; i< note.length(); i++){
          noteCharCount.put(note.charAt(i), noteCharCount.getOrDefault(note.charAt(i), 0) + 1);
      }
      for (String word: words){
          for (int i = 0; i< word.length(); i++){
                char c = word.charAt(i);
                if (noteCharCount.containsKey(c)) {
                    noteCharCount.put(c, noteCharCount.get(c) - 1);
                    if (noteCharCount.get(c) == 0) {
                        noteCharCount.remove(c);
                    }
                    if (noteCharCount.isEmpty()) {
                        return word;
                    }
                } else {
                    break;
                }
          }
      }
      return "-";
  }
}