package util;

import com.google.gson.*;
import constant.DateTimeConstant;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class FileUtil<T> implements DataWritable<T>, DataReadable<T> {



    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext jsonSerializationContext) {
                    return new JsonPrimitive(date.format(DateTimeConstant.DATE_FORMATTER));
                }
            })
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString(), DateTimeConstant.DATE_FORMATTER);
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

                @Override
                public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                    return new JsonPrimitive(formatter.format(localDateTime));
                }
            })
            .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

                @Override
                public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    return LocalDateTime.parse(json.getAsString(),
                            DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH));
                }
            })
            .create();


    @Override
    public void writeDataToFile(List<T> data, String fileName) {
        if (StringUtil.isNullOrEmpty(fileName) || DataUtil.isNullOrEmpty(data)) {
            return;
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String rs = gson.toJson(data);
            fileWriter.write(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<T> readDataFromFile(String fileName, Class<T[]> clazz) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            return null;
        }
        try (FileReader reader = new FileReader(fileName)) {
            T[] dataArr = gson.fromJson(reader, clazz);
            return dataArr == null ? null : new ArrayList<>(Arrays.asList(dataArr));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
