package com.ll;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class 기능개발_Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] workDays = getWorkDays(progresses, speeds);

        Stack<Integer> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();
        int cnt = 0;

        for(int i=0 ; i<workDays.length ; i++){
            if(stack.size() > 0) {
                if(workDays[i] > stack.stream().max(Integer::compare).get()) {
                    cnt = stack.size();
                    stack.clear();
                    result.add(cnt);
                }
            }

            stack.push(workDays[i]);
        }

        if(stack.size() > 0) {
            cnt = stack.size();
            stack.clear();
            result.add(cnt);
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] getWorkDays(int[] progresses, int[] speeds) {
        int[] times = new int[progresses.length];
        for(int i=0 ; i< progresses.length ; i++) {
            times[i] = (int) Math.ceil(100.0 - progresses[i]) / speeds[i];
        }
        return times;
    }
}

class 기능개발_Solution2 {
    public int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = IntStream
                .range(0, progresses.length)
                .mapToObj(i -> (int) Math.ceil((100.0 - progresses[i]) / speeds[i]))
                .collect(Collectors.toCollection(LinkedList::new));

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int remains = queue.poll();
            int count = 1;

            while (!queue.isEmpty() && remains >= queue.peek()) {
                queue.poll();
                count++;
            }

            result.add(count);
        }

        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}

class 기능개발_Solution3 {
    public int[] solution(int[] progresses, int[] speeds) {
        IntStream.range(0, progresses.length)
                .forEach(i -> {
                    // [7,3,9] -> [7,7,9]
                    int day = (int) Math.ceil((100.0 - progresses[i]) / speeds[i]);
                    progresses[i] = day;
                    if (i > 0) progresses[i] = Math.max(progresses[i - 1], progresses[i]);
                });

        return Arrays
                .stream(progresses)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, LinkedHashMap::new, Collectors.counting()))
                .values()
                .stream()
                .mapToInt(Long::intValue)
                .toArray(); // [7,7,9] -> (7,7)  (9) -> [2,1]
    }
}
