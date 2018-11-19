package training.eight.c;

import training.Downloader;
import training.IDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        IDownloader downloader = new Downloader();
        Path path = Paths.get("src/main/resources/eight_c.input");
        List<String> data = downloader.download(path);
        List<Node> tree = fillTree(data);
        System.out.println("Result: " + calculateWinner(tree));
    }

    private static int calculateWinner(List<Node> tree) {
        for (int i = tree.size() - 1; i > 0; i--) {
            Node currentNode = tree.get(i);
            Node parentNode = tree.get(currentNode.parentIndex);
            if (parentNode.whoStep == 1) {
                parentNode.winResult = Math.max(parentNode.winResult, currentNode.winResult);
            } else {
                parentNode.winResult = Math.min(parentNode.winResult, currentNode.winResult);
            }
        }
        return tree.get(0).winResult;
    }

    private static List<Node> fillTree(List<String> data) {
        List<Node> tree = new ArrayList<>(Integer.parseInt(data.get(0)));
        tree.add(new Node(-1, 1, -1));
        for (int i = 1; i < data.size(); i++) {
            String nodeAsString = data.get(i);
            String[] nodeAsArrayString = nodeAsString.split(" ");
            Node node;
            int parentIndex = Integer.parseInt(nodeAsArrayString[1]) - 1;
            int whoStep = tree.get(parentIndex).whoStep == 1 ? 2 : 1;
            if (nodeAsArrayString.length == 2) {
                node = new Node(parentIndex, whoStep, whoStep == 1 ? -1 : 1);
            } else {
                node = new Node(parentIndex, whoStep, Integer.parseInt(nodeAsArrayString[2]));
            }
            tree.add(node);
        }
        return tree;
    }


    private static class Node {
        int winResult;
        final int parentIndex;
        final int whoStep;


        private Node(int parentIndex, int whoStep, int winResult) {
            this.parentIndex = parentIndex;
            this.whoStep = whoStep;
            this.winResult = winResult;
        }
    }

}
