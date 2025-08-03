package com.github.tommyettinger;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.cyphercove.gdxtokryo.gdxserializers.utils.GdxArraySerializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.luben.zstd.Zstd;
import com.github.tommyettinger.tantrum.libgdx.ArraySerializer;
import com.github.tommyettinger.tantrum.libgdx.QueueSerializer;
import com.github.tommyettinger.tantrum.libgdx.Vector3Serializer;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import org.apache.fory.Fory;
import org.apache.fory.config.Language;
import org.apache.fory.meta.DeflaterMetaCompressor;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class SizeComparisonTest {
    /**
     * Fory bytes length : 53
     * Fory with Zstd    : 62
     * Fory with LZ4     : 55
     * Fory with Deflate : 53
     * Kryo bytes length : 47
     * Kryo with Zstd    : 56
     * Kryo with LZ4     : 49
     * Kryo with Deflate : 52
     * JSON bytes length : 33
     * JSON with Zstd    : 42
     * JSON with LZ4     : 35
     * JSON with Deflate : 39
     */
    @Test
    public void testSmallStringArray() {
        System.out.println("Small Array<String>");

        Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
        fory.registerSerializer(Array.class, new ArraySerializer(fory));
        fory.register(Array.class);

        Json json = new Json();

        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(Array.class, new GdxArraySerializer());

        Array<String> data = Array.with("Hello", "World", "!", "I", "am", "a", "test", "!", "yay");

        LZ4Factory lz4Factory = net.jpountz.lz4.LZ4Factory.fastestInstance();
        LZ4Compressor lz4Compressor = lz4Factory.fastCompressor();
        LZ4FastDecompressor lz4Decompressor = lz4Factory.fastDecompressor();

        DeflaterMetaCompressor deflate = new DeflaterMetaCompressor();

        byte[] bytesFory = fory.serializeJavaObject(data);
        {
            Array<?> data2 = fory.deserializeJavaObject(bytesFory, Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyZstd = Zstd.compress(bytesFory);
        {
            Array<?> data2 = fory.deserializeJavaObject(Zstd.decompress(bytesForyZstd, (int)Zstd.getFrameContentSize(bytesForyZstd)), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyLz4 = lz4Compressor.compress(bytesFory);
        {
            Array<?> data2 = fory.deserializeJavaObject(lz4Decompressor.decompress(bytesForyLz4, bytesFory.length), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyDeflate = deflate.compress(bytesFory, 0, bytesFory.length);
        {
            Array<?> data2 = fory.deserializeJavaObject(deflate.decompress(bytesForyDeflate, 0, bytesForyDeflate.length), Array.class);
            Assert.assertEquals(data, data2);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        Output output = new Output(baos);
        kryo.writeObject(output, data);
        output.close();
        byte[] bytesKryo = baos.toByteArray();
        {
            Array<?> data2 = kryo.readObject(new Input(bytesKryo), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoZstd = Zstd.compress(bytesKryo);
        {
            Array<?> data2 = kryo.readObject(new Input(Zstd.decompress(bytesKryoZstd, (int)Zstd.getFrameContentSize(bytesKryoZstd))), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoLz4 = lz4Compressor.compress(bytesKryo);
        {
            Array<?> data2 = kryo.readObject(new Input(lz4Decompressor.decompress(bytesKryoLz4, bytesKryo.length)), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoDeflate = deflate.compress(bytesKryo, 0, bytesKryo.length);
        {
            Array<?> data2 = kryo.readObject(new Input(deflate.decompress(bytesKryoDeflate, 0, bytesKryoDeflate.length)), Array.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Array.class, String.class);
        byte[] bytesJson = text.getBytes(StandardCharsets.ISO_8859_1);
        {
            Array<?> data2 = json.fromJson(Array.class, String.class, text);;
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonZstd = Zstd.compress(bytesJson);
        {
            String textJsonZstd = new String(Zstd.decompress(bytesJsonZstd, (int)Zstd.getFrameContentSize(bytesJsonZstd)), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonZstd);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonLz4 = lz4Compressor.compress(bytesJson);
        {
            String textJsonLz4 = new String(lz4Decompressor.decompress(bytesJsonLz4, bytesJson.length), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonLz4);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonDeflate = deflate.compress(bytesJson, 0, bytesJson.length);
        {
            String textJsonLz4 = new String(deflate.decompress(bytesJsonDeflate, 0, bytesJsonDeflate.length), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonLz4);
            Assert.assertEquals(data, data2);
        }

        System.out.println("Fory bytes length : " + bytesFory.length);
        System.out.println("Fory with Zstd    : " + bytesForyZstd.length);
        System.out.println("Fory with LZ4     : " + bytesForyLz4.length);
        System.out.println("Fory with Deflate : " + bytesForyDeflate.length);
        System.out.println("Kryo bytes length : " + bytesKryo.length);
        System.out.println("Kryo with Zstd    : " + bytesKryoZstd.length);
        System.out.println("Kryo with LZ4     : " + bytesKryoLz4.length);
        System.out.println("Kryo with Deflate : " + bytesKryoDeflate.length);
        System.out.println("JSON bytes length : " + bytesJson.length);
        System.out.println("JSON with Zstd    : " + bytesJsonZstd.length);
        System.out.println("JSON with LZ4     : " + bytesJsonLz4.length);
        System.out.println("JSON with Deflate : " + bytesJsonDeflate.length);
//        System.out.println("JSON data: " + text);
    }

    /**
     * Fory bytes length : 10072
     * Fory with Zstd    : 2847
     * Fory with LZ4     : 8141
     * Fory with Deflate : 4110
     * Kryo bytes length : 9049
     * Kryo with Zstd    : 2848
     * Kryo with LZ4     : 8038
     * Kryo with Deflate : 4161
     * JSON bytes length : 8021
     * JSON with Zstd    : 3738
     * JSON with LZ4     : 7922
     * JSON with Deflate : 3341
     */
    @Test
    public void testLargeStringArray() {
        System.out.println("Large Array<String>");

        Fory fory = Fory.builder().withLanguage(Language.JAVA)
                .requireClassRegistration(true)
                .build();
        fory.registerSerializer(Array.class, new ArraySerializer(fory));
        fory.register(Array.class);

        Json json = new Json();

        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        kryo.register(Array.class, new GdxArraySerializer());

        Array<String> data = new Array<>(true, 1024, String.class);

        for (int i = 0; i < 1024; i++) {
            data.add(i + " " + i);
        }

        LZ4Factory lz4Factory = net.jpountz.lz4.LZ4Factory.fastestInstance();
        LZ4Compressor lz4Compressor = lz4Factory.fastCompressor();
        LZ4FastDecompressor lz4Decompressor = lz4Factory.fastDecompressor();

        DeflaterMetaCompressor deflate = new DeflaterMetaCompressor();

        byte[] bytesFory = fory.serializeJavaObject(data);
        {
            Array<?> data2 = fory.deserializeJavaObject(bytesFory, Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyZstd = Zstd.compress(bytesFory);
        {
            Array<?> data2 = fory.deserializeJavaObject(Zstd.decompress(bytesForyZstd, (int)Zstd.getFrameContentSize(bytesForyZstd)), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyLz4 = lz4Compressor.compress(bytesFory);
        {
            Array<?> data2 = fory.deserializeJavaObject(lz4Decompressor.decompress(bytesForyLz4, bytesFory.length), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesForyDeflate = deflate.compress(bytesFory, 0, bytesFory.length);
        {
            Array<?> data2 = fory.deserializeJavaObject(deflate.decompress(bytesForyDeflate, 0, bytesForyDeflate.length), Array.class);
            Assert.assertEquals(data, data2);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        Output output = new Output(baos);
        kryo.writeObject(output, data);
        output.close();
        byte[] bytesKryo = baos.toByteArray();
        {
            Array<?> data2 = kryo.readObject(new Input(bytesKryo), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoZstd = Zstd.compress(bytesKryo);
        {
            Array<?> data2 = kryo.readObject(new Input(Zstd.decompress(bytesKryoZstd, (int)Zstd.getFrameContentSize(bytesKryoZstd))), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoLz4 = lz4Compressor.compress(bytesKryo);
        {
            Array<?> data2 = kryo.readObject(new Input(lz4Decompressor.decompress(bytesKryoLz4, bytesKryo.length)), Array.class);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesKryoDeflate = deflate.compress(bytesKryo, 0, bytesKryo.length);
        {
            Array<?> data2 = kryo.readObject(new Input(deflate.decompress(bytesKryoDeflate, 0, bytesKryoDeflate.length)), Array.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Array.class, String.class);
        byte[] bytesJson = text.getBytes(StandardCharsets.ISO_8859_1);
        {
            Array<?> data2 = json.fromJson(Array.class, String.class, text);;
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonZstd = Zstd.compress(bytesJson);
        {
            String textJsonZstd = new String(Zstd.decompress(bytesJsonZstd, (int)Zstd.getFrameContentSize(bytesJsonZstd)), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonZstd);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonLz4 = lz4Compressor.compress(bytesJson);
        {
            String textJsonLz4 = new String(lz4Decompressor.decompress(bytesJsonLz4, bytesJson.length), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonLz4);
            Assert.assertEquals(data, data2);
        }
        byte[] bytesJsonDeflate = deflate.compress(bytesJson, 0, bytesJson.length);
        {
            String textJsonLz4 = new String(deflate.decompress(bytesJsonDeflate, 0, bytesJsonDeflate.length), StandardCharsets.ISO_8859_1);
            Array<?> data2 = json.fromJson(Array.class, String.class, textJsonLz4);
            Assert.assertEquals(data, data2);
        }

        System.out.println("Fory bytes length : " + bytesFory.length);
        System.out.println("Fory with Zstd    : " + bytesForyZstd.length);
        System.out.println("Fory with LZ4     : " + bytesForyLz4.length);
        System.out.println("Fory with Deflate : " + bytesForyDeflate.length);
        System.out.println("Kryo bytes length : " + bytesKryo.length);
        System.out.println("Kryo with Zstd    : " + bytesKryoZstd.length);
        System.out.println("Kryo with LZ4     : " + bytesKryoLz4.length);
        System.out.println("Kryo with Deflate : " + bytesKryoDeflate.length);
        System.out.println("JSON bytes length : " + bytesJson.length);
        System.out.println("JSON with Zstd    : " + bytesJsonZstd.length);
        System.out.println("JSON with LZ4     : " + bytesJsonLz4.length);
        System.out.println("JSON with Deflate : " + bytesJsonDeflate.length);
//        System.out.println("JSON data: " + text);
    }

    @Test
    public void testSmallVector3Array() {
        MathUtils.random.setSeed(1234567890L);
        Fory fory = Fory.builder().withLanguage(Language.JAVA).withRefTracking(true).build();
        fory.registerSerializer(Array.class, new ArraySerializer(fory));
        fory.register(Array.class);
        fory.register(Array.ArrayIterable.class);
        fory.register(Array.ArrayIterator.class);
        fory.registerSerializer(Vector3.class, new Vector3Serializer(fory));

        Json json = new Json();

        Array<Vector3> data = new Array<>(9);
        Vector3 sum = new Vector3();
        for (int i = 0; i < 9; i++) {
            data.add(new Vector3(MathUtils.random(), MathUtils.random(), MathUtils.random()));
        }
        for(Vector3 d : data){
            sum.add(d);
        }
        data.add(sum);

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Array<?> data2 = fory.deserializeJavaObject(bytes, Array.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Array.class, Vector3.class);
        {
            Array<?> data2 = json.fromJson(Array.class, Vector3.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Small Array<Vector3>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }

    @Test
    public void testLargeVector3Array() {
        MathUtils.random.setSeed(1234567890L);
        Fory fory = Fory.builder().withLanguage(Language.JAVA).withRefTracking(true).build();
        fory.registerSerializer(Array.class, new ArraySerializer(fory));
        fory.register(Array.class);
        fory.register(Array.ArrayIterable.class);
        fory.register(Array.ArrayIterator.class);
        fory.registerSerializer(Vector3.class, new Vector3Serializer(fory));

        Json json = new Json();

        Array<Vector3> data = new Array<>(1024);
        Vector3 sum = new Vector3();
        for (int i = 0; i < 1024; i++) {
            data.add(new Vector3(MathUtils.random(), MathUtils.random(), MathUtils.random()));
        }
        for(Vector3 d : data){
            sum.add(d);
        }
        data.add(sum);

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Array<?> data2 = fory.deserializeJavaObject(bytes, Array.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Array.class, Vector3.class);
        {
            Array<?> data2 = json.fromJson(Array.class, Vector3.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Large Array<Vector3>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }

    @Test
    public void testSmallStringQueue() {
        Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
        fory.registerSerializer(Queue.class, new QueueSerializer(fory));

        Json json = new Json();

        Queue<String> data = new Queue<>(9);
        for(String s : new String[]{"Hello", "World", "!", "I", "am", "a", "test", "!", "yay"}) {
            data.addLast(s);
        }

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Queue<?> data2 = fory.deserializeJavaObject(bytes, Queue.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Queue.class, String.class);
        {
            Queue<?> data2 = json.fromJson(Queue.class, String.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Small Queue<String>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }

    @Test
    public void testLargeStringQueue() {
        Fory fory = Fory.builder().withLanguage(Language.JAVA)
                .requireClassRegistration(true)
                .build();
        fory.registerSerializer(Queue.class, new QueueSerializer(fory));

        Json json = new Json();

        Queue<String> data = new Queue<>(1024);

        for (int i = 0; i < 1024; i++) {
            data.addLast(i + " " + i);
        }

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Queue<?> data2 = fory.deserializeJavaObject(bytes, Queue.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Queue.class, String.class);
        {
            Queue<?> data2 = json.fromJson(Queue.class, String.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Large Queue<String>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }

    @Test
    public void testSmallVector3Queue() {
        MathUtils.random.setSeed(1234567890L);
        Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
        fory.registerSerializer(Queue.class, new QueueSerializer(fory));
        fory.registerSerializer(Vector3.class, new Vector3Serializer(fory));

        Json json = new Json();

        Queue<Vector3> data = new Queue<>(9);
        for (int i = 0; i < 9; i++) {
            data.addLast(new Vector3(MathUtils.random(), MathUtils.random(), MathUtils.random()));
        }

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Queue<?> data2 = fory.deserializeJavaObject(bytes, Queue.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Queue.class, Vector3.class);
        {
            Queue<?> data2 = json.fromJson(Queue.class, Vector3.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Small Queue<Vector3>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }

    @Test
    public void testLargeVector3Queue() {
        MathUtils.random.setSeed(1234567890L);
        Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
        fory.registerSerializer(Queue.class, new QueueSerializer(fory));
        fory.registerSerializer(Vector3.class, new Vector3Serializer(fory));

        Json json = new Json();

        Queue<Vector3> data = new Queue<>(1024);
        Vector3 sum = new Vector3();
        for (int i = 0; i < 1024; i++) {
            data.addLast(new Vector3(MathUtils.random(), MathUtils.random(), MathUtils.random()));
        }
        for(Vector3 d : data){
            sum.add(d);
        }
        data.addLast(sum);

        byte[] bytes = fory.serializeJavaObject(data);
        {
            Queue<?> data2 = fory.deserializeJavaObject(bytes, Queue.class);
            Assert.assertEquals(data, data2);
        }

        String text = json.toJson(data, Queue.class, Vector3.class);
        {
            Queue<?> data2 = json.fromJson(Queue.class, Vector3.class, text);;
            Assert.assertEquals(data, data2);
        }

        System.out.println("Large Queue<Vector3>");
        System.out.println("Fory bytes length : " + bytes.length);
        System.out.println("Fory String length: " + new String(bytes, StandardCharsets.ISO_8859_1).length());
        System.out.println("JSON bytes length : " + text.getBytes(StandardCharsets.ISO_8859_1).length);
        System.out.println("JSON String length: " + text.length());
        System.out.println("JSON data: " + text);
    }
}
