import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Dice[] dice;
    private int dicenum;
    private String prizename;
    private Prize prizePool = new Prize();
    private Map<String, Map<String, Integer>> playerAwards = new HashMap<>();
    private int top = 0;
    private String topname = null;

    public Game(int dicenum){
        this.dicenum = dicenum;
        dice = new Dice[dicenum];
        for(int i=0;i<dicenum;i++){
            dice[i]=new Dice();
        }
    }

    public String calculate(int i){
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int j = 0; j < dicenum; j++) {
            int value = dice[j].getNum();
            countMap.put(value, countMap.getOrDefault(value, 0) + 1);
        }

        if (countMap.getOrDefault(6, 0) == 1 && countMap.getOrDefault(5, 0) == 1 &&
                countMap.getOrDefault(4, 0) == 1 && countMap.getOrDefault(3, 0) == 1 &&
                countMap.getOrDefault(2, 0) == 1 && countMap.getOrDefault(1, 0) == 1 ) {
            if(!prizePool.decPrize("对堂")){
                return "对堂奖品已不足";
            }
            return "对堂";
        } else if (countMap.getOrDefault(4, 0) == 4 && countMap.getOrDefault(2, 0) == 2) {
            if(top < 7){
                top = 7;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "状元插金花";
        } else if (countMap.getOrDefault(6, 0) == 6 || countMap.getOrDefault(5, 0) == 6 ||
                countMap.getOrDefault(3, 0) == 6 || countMap.getOrDefault(2, 0) == 6) {
            if(top < 5){
                top = 5;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "六勃黑";
        } else if (countMap.getOrDefault(1, 0) == 6) {
            if ( top < 6){
                top = 6;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "遍地锦";
        } else if (countMap.getOrDefault(4, 0) == 6) {
            if ( top < 4){
                top = 4;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "六勃红";
        } else if (countMap.getOrDefault(4, 0) == 5) {
            if ( top < 3){
                top = 3;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "五王";
        } else if (countMap.getOrDefault(6, 0) == 5 || countMap.getOrDefault(5, 0) == 5 ||
                countMap.getOrDefault(3, 0) == 5 || countMap.getOrDefault(2, 0) == 5 ) {
            if ( top < 2){
                top = 2;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "五子";
        } else if (countMap.getOrDefault(4, 0) == 4) {
            if ( top < 1){
                top = 1;
                topname="玩家"+i;
            }
            prizePool.decPrize("状元");
            return "四红";
        } else if (countMap.getOrDefault(1, 0) == 4) {
            if (countMap.getOrDefault(4, 0) == 2){
                if(!prizePool.decPrize("四进")){
                    return "四进奖品已不足";
                }
                if(!prizePool.decPrize("二举")){
                    return "二举奖品已不足";
                }
                return "四进和二举";
            } else if (countMap.getOrDefault(4, 0) == 1) {
                if(!prizePool.decPrize("四进")){
                    return "四进奖品已不足";
                }
                if(!prizePool.decPrize("一秀")){
                    return "一秀奖品已不足";
                }
                return "四进和一秀";
            }
            else {
                if(!prizePool.decPrize("四进")){
                    return "四进奖品已不足";
                }
                return "四进";
            }
        } else if (countMap.getOrDefault(1, 0) == 3) {
            if (countMap.getOrDefault(4, 0) == 2){
                if(!prizePool.decPrize("三红")){
                    return "三红奖品已不足";
                }
                if(!prizePool.decPrize("二举")){
                    return "二举奖品已不足";
                }
                return "三红和二举";
            } else if (countMap.getOrDefault(4, 0) == 1) {
                if(!prizePool.decPrize("三红")){
                    return "三红奖品已不足";
                }
                if(!prizePool.decPrize("一秀")){
                    return "一秀奖品已不足";
                }
                return "三红和一秀";
            }
            else {
                if(!prizePool.decPrize("三红")){
                    return "三红奖品已不足";
                }
                return "三红";
            }
        } else if (countMap.getOrDefault(4, 0) == 2) {
            if(!prizePool.decPrize("二举")){
                return "二举奖品已不足";
            }
            return "二举";
        } else if (countMap.getOrDefault(4, 0) == 1) {
            if(!prizePool.decPrize("一秀")){
                return "一秀奖品已不足";
            }
            return "一秀";
        } else {
            return "无奖励";
        }
    }

    public void genDice(){
        for(int i=0;i<dicenum;i++){
            dice[i].roll();
        }
    }

    public void disAwards(String prizeName, String playerName) {
        Map<String, Integer> awards = playerAwards.getOrDefault(playerName, new HashMap<>());
        if (prizeName.contains("和")) {
            String[] parts = prizeName.split("和");  //处理带“和”的奖项
            for (String part : parts) {
                awards.put(part, awards.getOrDefault(part, 0) + 1);
            }
        } else {
            awards.put(prizeName, awards.getOrDefault(prizeName, 0) + 1);
        }
        playerAwards.put(playerName, awards);
    }

    private void updateTop(String topname) {
        Map<String, Integer> awards = playerAwards.getOrDefault(topname, new HashMap<>());
        awards.put("状元", awards.getOrDefault("状元", 0) + 1);
        playerAwards.put(topname, awards);
    }

    public void printFinalAwards() {
        System.out.println();
            System.out.println("所有玩家的最终获奖情况：");
            playerAwards.forEach((playerName, awards) -> {
                System.out.println(playerName + " 获得的奖项:");
                awards.forEach((prize, count) -> System.out.println("  " + prize + ": " + count + "个"));
                System.out.println();
            });
    }



    public void startGame(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("准备游戏中...");
        System.out.println("请输入游戏玩家数(6-10人):");
        int playerCount = scanner.nextInt();
        while (playerCount < 6 || playerCount > 10) {
            System.out.print("请输入有效的玩家人数（6-10人）：");
            playerCount = scanner.nextInt();
        }

        for (int i = 1; i <= playerCount; i++) {
            playerAwards.put("玩家" + i, new HashMap<>());
        }

        System.out.println("博饼游戏开始！");
        while (!prizePool.ALLNoPrize()) {
            for (int i = 1; i <= playerCount; i++) {
                System.out.println("开始投骰子...");
                genDice();
                System.out.print("玩家" + i + " 摇出的骰子: ");
                for (Dice d : dice) {
                    System.out.print(d.getNum() + " ");
                }
                System.out.println();  // 换行
                System.out.print("所得结果为：");
                prizename=calculate(i);
                System.out.println(prizename);
                if (!"无奖励".equals(prizename) && !prizename.contains("已不足")) {
                    //满足条件后分配奖项
                    disAwards(prizename, "玩家" + i);
                }
            }

        }
        updateTop(topname);
        printFinalAwards();
    }
}
