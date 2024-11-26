package com.project.app.services.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Iterator;

public class CsvUtil
{
    public static void downloadCsv(ArrayNode banks, HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=reports.csv");

            CSVWriter csvWriter = new CSVWriter(new PrintWriter(response.getWriter()));

            if (banks != null && banks.size() > 0)
            {
                // Get the first element to extract the keys for the header
                JsonNode firstRow = banks.get(0);
                Iterator<String> fieldNames = firstRow.fieldNames();
                String[] header = new String[firstRow.size()];
                int index = 0;

                // Collect header names
                while (fieldNames.hasNext())
                {
                    header[index++] = fieldNames.next();
                }
                csvWriter.writeNext(header);

                // Write data rows
                for (JsonNode bank : banks)
                {
                    String[] rowData = new String[header.length];
                    for (int i = 0; i < header.length; i++) {
                        rowData[i] = bank.has(header[i]) ? bank.get(header[i]).asText("") : "";
                    }
                    csvWriter.writeNext(rowData);
                }
            }

            // Close CSV writer
            csvWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
