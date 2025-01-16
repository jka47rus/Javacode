package main;

import java.util.ArrayList;
import java.util.List;

public class Creator {
    private List<Snapshot> snapshots = new ArrayList<>();

    public void saveSnapshot(String word) {
        snapshots.add(new Snapshot(word));
    }

    public String restoreSnapshot(int index) {
        String answer;
        if (index < 0 || index >= snapshots.size()) {
            answer = "Некорректный индекс!";
            System.out.println(answer);
            return answer;
        }
        answer = snapshots.get(index).getContent();
        System.out.println(answer);
        return answer;
    }

    public String showSnapshots() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < snapshots.size(); i++) {
            sb.append("Индекс: ").append(i).append("; Содержимое: ").append(snapshots.get(i).getContent()).append("\n");
        }
        String answer = sb.toString();
        System.out.println(answer);
        return answer;
    }

    public String printTillTheIndex(int index) {
        String answer;
        if (index < 0 || index >= snapshots.size()) {
            answer = "Некорректный индекс!";
            System.out.println(answer);
            return answer;
        }
        StringBuilder sb = new StringBuilder();
        snapshots.forEach(word -> sb.append(word.getContent()));
        answer = sb.toString();
        System.out.println(answer);
        return answer;
    }

}
