package com.ll;

import java.util.HashMap;

class 체육복_Solution {

    public int solution(int n, int[] lost, int[] reserve) {
        HashMap<Integer, Integer> students = new HashMap<>();

        for(int i=1 ; i<=n ; i++)
            students.put(i, 1);

        for(int no : reserve)
            students.replace(no, students.get(no)+1);

        for(int no : lost)
            students.replace(no, students.get(no)-1);

        for(int no=1 ; no<=n ; no++)
            if(students.get(no) > 1)
                if(1 <= no - 1 && students.get(no - 1) == 0) {
                    students.replace(no-1, students.get(no-1)+1);
                    students.replace(no, students.get(no)-1);
                }
                else if(no + 1 <= n && students.get(no + 1) == 0) {
                    students.replace(no+1, students.get(no+1)+1);
                    students.replace(no, students.get(no)-1);
                }

        int sum = 0;
        for(int no=1 ; no<=n ; no++)
            if(students.get(no) >= 1)
                sum++;

        return sum;
    }
}