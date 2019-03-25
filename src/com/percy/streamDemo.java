package com.percy;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author percy
 * @create 2019-03-20  下午6:18
 * @descreption:
 **/
public class streamDemo {
    public static void main(String[] args) {
        try {
            System.out.println(Paths.get("/home/percy/IdeaProjects/StreamDemo/src/com/percy/God Had to Be Fair"));

            String contents = new String(Files.readAllBytes(Paths.get("/home/percy/IdeaProjects/StreamDemo/src/com/percy/God Had to Be Fair")), StandardCharsets.UTF_8);
            List<String> words = Arrays.asList(contents.split(" "));
            /**
             * 使用迭代计算长单词的数量
             */
            int count = 0;
            for (String word : words) {
                if (word.length() >= 12) {
                    count++;
                    System.out.println(word);
                }
            }
            /**
             * 利用流做统计长单词
             */
            long count0 = words.stream().filter(x -> x.length() >= 12).count();
            /**
             * 利用并行流统计长单词
             */
            long count1 = words.parallelStream().filter(w -> w.length() >= 12).count();
            /**
             * 流的创建，使用Stream.of静态方法,其中是一个字符串数组
             */
            Stream<String> stream = Stream.of(contents.split(" "));
            Stream<String> string0 = Stream.of("Alice", "Bob", "Driod", "Carl");
            Stream<String> string1 = Stream.of("Alice", "Bob", "Driod", "Carl");
            show("字符串数组字节流", string0);
            show("Sorted后的字符串", string1.sorted());
            //生成一个空流
            Stream<String> stream1 = Stream.empty();
            show("空流", stream1);
            Stream<String> finalstream = Stream.generate(() -> "Echo");
            show("generate生成无限常量流", finalstream);
            Stream<Double> randStream = Stream.generate(() -> Math.random());
            Stream<Double> randStream0 = Stream.generate(Math::random);
            show("generate生成随机double流", randStream);
            Stream<BigInteger> integerStream = Stream.iterate(BigInteger.ONE, x -> x.add(BigInteger.ONE));
            Stream<BigInteger> integerStream0 = Stream.iterate(BigInteger.valueOf(2), x -> x.multiply(BigInteger.valueOf(2)));
            show("iterate产生无限流", integerStream0);

            Stream<String> mapStream = words.stream().map(String::toUpperCase);
            show("利用map生成一定格式的流", mapStream);
            Stream<String> mapStream0 = words.stream().map(s -> s.substring(0, 1));
            show("利用map和lamda表达式生成一定格式的流", mapStream0);

            Stream<String> string2 = Stream.of("Alice", "Bob", "Driod", "Carl", "Alice");
            show("distinct后的字符串流", string2.distinct());
            /**
             * Optional类型
             *
             */
            Optional<Object> optional = Optional.of("");
            String result = (String) optional.orElse("");
            show("String result = (String) optional.orElse(\"\")", Stream.of(result));

            String result0 = (String) optional.orElseGet(() -> Locale.getDefault().getDisplayName());
            show("orElseGet", Stream.of(result0));

            double x = 0;
            show("Optional<Double> inverse(Double x) x= 0", Stream.of(inverse(x)));
            x = 2;
            show("Optional<Double> inverse(Double x) x= 2", Stream.of(inverse(x)));

            show("2.0", Stream.of(inverse(2.0).flatMap(streamDemo::squareRoot)));
            show("0.0", Stream.of(inverse(.0).flatMap(streamDemo::squareRoot)));
            show("-2.0", Stream.of(inverse(-2.0).flatMap(streamDemo::squareRoot)));

            Stream stream2 = words.stream();
            stream2.forEach(System.out::print);

            //把流传到一个数组中去
            String[] strings = words.stream().toArray(String[]::new);

            show("把流传到一个数组中去利用数组构造器", Arrays.stream(strings));
            //针对流中元素收集到另一个目标中，有一个便捷方法collect可用

//            List<String> lists = words.stream().collect(Collectors.toList());
//            Set<String> lists = words.stream().collect(Collectors.toSet());
            TreeSet<String> lists = words.stream().collect(Collectors.toCollection(TreeSet::new));
            System.out.println("针对流中元素收集到另一个目标中，有一个便捷方法collect可用");
            for (String list : lists) {
                System.out.print(list + " ");
            }
            Stream<Locale> localeStream = Stream.of(Locale.getAvailableLocales());
            Map<String, String> lan = localeStream.collect(Collectors.toMap(Locale::getDisplayLanguage,
                    locale -> locale.getDisplayLanguage(locale), (existingValue, newValue) -> existingValue));
            for (Map.Entry<String, String> stringStringEntry : lan.entrySet()) {
                System.out.println(stringStringEntry.getKey() + "=" + stringStringEntry.getValue());
            }

            System.out.println("------------------------");
            Stream<Locale> localeStream1 = Stream.of(Locale.getAvailableLocales());
            //localeStream1.forEach(System.out::println);
            long count2 = stream.filter(w -> w.length() >= 12).count();
            System.out.println(count2);


            Person person0 = new Person(1,"hpt");
            Person person1 = new Person(2,"yh");
            Person person2 = new Person(3,"yzw");

            Stream<Person> personStream = Stream.of(person0,person1,person2);

            Map<Integer,String> idToname = personStream.collect(Collectors.toMap(Person::getId,Person::getName));
            System.out.println("idToname"+idToname.getClass().getName()+idToname);
            for (Map.Entry<Integer, String> integerStringEntry : idToname.entrySet()) {
                System.out.println(integerStringEntry.getKey()+"="+integerStringEntry.getValue());
            }
            /**
             * 生成一个Hashmap
             */
            personStream = Stream.of(person0,person1,person2);
            Map<Integer, Person> idToPerson = personStream.collect(Collectors.toMap(Person::getId, Function.identity()));
            System.out.println("idToPerson:"+idToPerson.getClass().getName()+idToPerson);
            for (Map.Entry<Integer, Person> integerPersonEntry : idToPerson.entrySet()) {
                System.out.println(integerPersonEntry.getKey()+"-"+integerPersonEntry.getValue().getName());
            }
            /**
             * 生成一个TreeMap
             */
            personStream = Stream.of(person0,person1,person2);
            idToPerson = personStream.collect(Collectors.toMap(Person::getId,Function.identity(),(existingValue,newValue)->{throw new IllegalStateException();},TreeMap::new));
            System.out.println("idToPerson:"+idToPerson.getClass().getName()+idToPerson);
            /**
             * 基本类型流
             */
            IntStream intStream0 = IntStream.generate(()->(int)(Math.random()*100));
            showIntStream("intStream0",intStream0);
            IntStream intStream1 = IntStream.range(0,10);
            showIntStream("intStream1",intStream1);
            IntStream intStream2 = IntStream.rangeClosed(1,10);
            showIntStream("intStream2",intStream2);
            Stream<Integer> integerStream1 = IntStream.rangeClosed(1,10).boxed();
            IntStream intStream3 = integerStream1.mapToInt(Integer::intValue);
            showIntStream("intStream3",intStream3);
            IntStream intStream4 = IntStream.generate(()->(int)(Math.random()*100)).limit(100);
            System.out.println("数据统计："+intStream4.summaryStatistics());
            /**
             * 并行流
             */
            int[] shortWords = new int[12];
            Stream<String> stream3 = words.parallelStream();
            stream3.forEach(s->{if (s.length()<12) shortWords[s.length()]++;});
            for (int shortWord : shortWords) {
                System.out.println(shortWord);
            }

            Map<Integer,Long> shortWordsCount = words.parallelStream().filter(s->s.length()<12).collect(groupingBy(String::length,counting()));
            for (Map.Entry<Integer, Long> integerLongEntry : shortWordsCount.entrySet()) {
                System.out.println(integerLongEntry.getKey()+"-"+integerLongEntry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    private static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    private static <T> void show(String str, Stream<T> stream) {
        final Integer SIZE = 10;
        //limit可以用来抽取子流，原流短就短，原流长就长
        //skip可以跳过前n个元素
        //concat（a，b）是a元素后跟着的b
        List<T> lists = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print("Title(" + str + "):");
        for (int i = 0; i < lists.size(); i++) {
            if (i > 0) System.out.print(",");
            if (i < SIZE) System.out.print(lists.get(i));
            else System.out.print("...");
        }
        System.out.println("");
    }
    private static <T> void showIntStream(String str, IntStream stream) {
        final Integer SIZE = 10;
        //limit可以用来抽取子流，原流短就短，原流长就长
        //skip可以跳过前n个元素
        //concat（a，b）是a元素后跟着的b
        int[] lists = stream.limit(SIZE + 1).toArray();
        System.out.print("基本类型流(" + str + "):");
        for (int i = 0; i < lists.length; i++) {
            if (i > 0) System.out.print(",");
            if (i < SIZE) System.out.print(lists[i]);
            else System.out.print("...");
        }
        System.out.println("");
    }
}

