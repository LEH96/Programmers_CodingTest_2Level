package com.ll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class 모의고사_Solution {
    public int[] solution(int[] answers) {
        int[] score = {0,0,0};
        int[] sol1 = {1,2,3,4,5};
        int[] sol2 = {2,1,2,3,2,4,2,5};
        int[] sol3 = {3,3,1,1,2,2,4,4,5,5};

        //점수 계산
        for(int i=0 ; i< answers.length ; i++) {
            if (sol1[i % 5] == answers[i])
                score[0] += 1;
            if (sol2[i % 8] == answers[i])
                score[1] += 1;
            if (sol3[i % 10] == answers[i])
                score[2] += 1;
        }

        //점수 비교
        int maxScore = Arrays.stream(score).max().getAsInt();
        return IntStream.rangeClosed(1, 3)
                .filter(no -> maxScore == score[no-1])
                .toArray();
    }
}

class 모의고사_Solution2 {
    public int[] solution(int[] answers) {
        Map<Integer, Long> rightAnswerCountsByStyleNo =
                IntStream.range(0, answers.length)// 0 ~ 4까지의 범위
                        .mapToObj(i -> { //answer에 적힌 답과 같은 답을 적은 수 만큼 학생의 번호를 추가
                            List<Integer> l = new ArrayList<>();

                            if (answers[i] == answerOfSt1(i)) l.add(1);
                            if (answers[i] == answerOfSt2(i)) l.add(2);
                            if (answers[i] == answerOfSt3(i)) l.add(3);

                            return l;
                        })
                        .flatMap(List::stream)//리스트를 1차원 스트림으로 변환
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); //그룹핑하고 각 학생들의 정답 수를 맵형태로 저장

        long max = rightAnswerCountsByStyleNo
                .values()
                .stream()
                .max(Long::compareTo)
                .get();

        return rightAnswerCountsByStyleNo
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == max)
                .mapToInt(Map.Entry::getKey)
                .sorted()
                .toArray();
    }

    int answerOfSt1(int i) {
        int[] answers = new int[]{1, 2, 3, 4, 5};
        return answers[i % answers.length];
    }

    int answerOfSt2(int i) {
        int[] answers = new int[]{2, 1, 2, 3, 2, 4, 2, 5};
        return answers[i % answers.length];
    }

    int answerOfSt3(int i) {
        int[] answers = new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        return answers[i % answers.length];
    }
}