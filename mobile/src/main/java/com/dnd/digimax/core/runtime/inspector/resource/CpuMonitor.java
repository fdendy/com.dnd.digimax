package com.dnd.digimax.core.runtime.inspector.resource;

import android.os.Build;
import android.os.Process;

import com.dnd.digimax.core.system.watchdog.LogsSystemWriter;

import java.io.BufferedReader;
import java.io.FileReader;

public class CpuMonitor {
    private String strTAG = "CpuMonitor";
    private LogsSystemWriter logs = new LogsSystemWriter();
    // MAIN HEALTH CHECK =========================
    public boolean isOverloaded() {
        return getCpuUsage() > 85;
    }
    public boolean isHealthy() {
        int cpu = getCpuUsage();
        if(cpu>75)logs.systemWriter(strTAG, "CPU LOW"+ cpu);
        // threshold bisa kamu adjust sesuai device class
//        return cpu >= 0 && cpu <= 75;
        return cpu >= 0 && cpu <= 99;
    }
    public int getCpuUsage() {
        try {
            long[] cpu1 = readCpuStat();
            Thread.sleep(200);
            long[] cpu2 = readCpuStat();
            long idle1 = cpu1[0];
            long total1 = cpu1[1];
            long idle2 = cpu2[0];
            long total2 = cpu2[1];
            long idleDiff = idle2 - idle1;
            long totalDiff = total2 - total1;
            if (totalDiff == 0) return 0;
            return (int) ((totalDiff - idleDiff) * 100 / totalDiff);
        } catch (Exception e) {
            return 0;
        }
    }
    private long[] readCpuStat() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"));
        String load = reader.readLine();
        reader.close();
        String[] toks = load.split(" +");
        long idle = Long.parseLong(toks[4]);
        long total = 0;
        for (String tok : toks) {
            if (tok.matches("\\d+")) {
                total += Long.parseLong(tok);
            }
        }

        return new long[]{idle, total};
    }
}