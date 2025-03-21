/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.examples;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedSet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.ds.ObjectObjectMap;
import com.github.tommyettinger.ds.Utilities;
import com.github.tommyettinger.kryo.jdkgdxds.ObjectListSerializer;
import com.github.tommyettinger.kryo.jdkgdxds.ObjectObjectMapSerializer;
import com.github.tommyettinger.random.FourWheelRandom;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import squidpony.StringKit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoMoreWriteBench score: 645.111816 (645.1 646.9%)
 *                uncertainty:   0.2%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoMoreWriteBench score: 641.252441 (641.3 646.3%)
 *                uncertainty:   4.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * KryoMoreWriteBench score: 643.034302 (643.0 646.6%)
 *                uncertainty:   6.5%
 * <br>
 * GraalVM Java 22:
 * <br>
 * KryoMoreWriteBench score: 691.737305 (691.7 653.9%)
 *                uncertainty:   3.2%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoMoreWriteBench score: 627.062500 (627.1 644.1%)
 *                uncertainty:   3.7%
 */

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoMoreWriteBench score: 619.414856 (619.4 642.9%)
 *                uncertainty:   2.6%
 */
public final class KryoMoreWriteBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		String book = "";
		try {
			book = new String(Files.readAllBytes(Paths.get("res/bible_only_words.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String[] words = StringKit.split(book, " ");
		OrderedSet<String> unique = OrderedSet.with(words);
		ObjectObjectMap<String, ObjectList<Vector2>> big = new ObjectObjectMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, ObjectList.with(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			));
		}

		Kryo kryo = new Kryo();
		kryo.register(Vector2.class);
		kryo.register(ObjectList.class, new ObjectListSerializer());
		kryo.register(ObjectObjectMap.class, new ObjectObjectMapSerializer());
//		kryo.register(ObjectList.class, new CollectionSerializer<ObjectList<?>>(){
//			@Override
//			protected ObjectList<?> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectList<>(size);
//			}
//
//			@Override
//			protected ObjectList<?> createCopy(Kryo kryo, ObjectList original) {
//				return new ObjectList<>(original.size());
//			}
//		});
//		kryo.register(ObjectObjectMap.class, new MapSerializer<ObjectObjectMap<?, ?>>(){
//			@Override
//			protected ObjectObjectMap<?, ?> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectObjectMap<>(size, Utilities.getDefaultLoadFactor());
//			}
//
//			@Override
//			protected ObjectObjectMap<?, ?> createCopy(Kryo kryo, ObjectObjectMap<?, ?> original) {
//				return new ObjectObjectMap<>(original.size());
//			}
//		});

//		Kryo kryo = new Kryo();
//		kryo.register(Vector2.class);
//		CollectionSerializer<ObjectList<Vector2>> cs = new CollectionSerializer<>();
//		cs.setElementClass(Vector2.class);
//		kryo.register(ObjectList.class, cs);
//		MapSerializer<ObjectObjectMap<String, ObjectList<Vector2>>> ms = new MapSerializer<>();
//		ms.setKeyClass(String.class);
//		ms.setValueClass(ObjectList.class, cs);
//		kryo.register(ObjectObjectMap.class, ms);

		long counter = 0;

		Output output = new Output(65536, -1);

		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				output.reset();
				startTimer();
				kryo.writeObject(output, big);
				pauseTimer();
				counter += output.total();
			}
		}
		return numLoops * numIterationsPerLoop;
	}

	public static void main(String[] args) {
		String book = "";
		try {
			book = new String(Files.readAllBytes(Paths.get("res/bible_only_words.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String[] words = StringKit.split(book, " ");
		OrderedSet<String> unique = OrderedSet.with(words);
		ObjectObjectMap<String, ObjectList<Vector2>> big = new ObjectObjectMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, ObjectList.with(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			));
		}

		System.out.println("There are " + big.size() + " keys in the Map.");

		Kryo kryo = new Kryo();
		kryo.register(Vector2.class);
		kryo.register(ObjectList.class, new ObjectListSerializer());
		kryo.register(ObjectObjectMap.class, new ObjectObjectMapSerializer());
//		kryo.register(ObjectList.class, new CollectionSerializer<ObjectList<?>>(){
//			@Override
//			protected ObjectList<?> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectList<>(size);
//			}
//
//			@Override
//			protected ObjectList<?> createCopy(Kryo kryo, ObjectList original) {
//				return new ObjectList<>(original.size());
//			}
//		});
//		kryo.register(ObjectObjectMap.class, new MapSerializer<ObjectObjectMap<?, ?>>(){
//			@Override
//			protected ObjectObjectMap<?, ?> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectObjectMap<>(size, Utilities.getDefaultLoadFactor());
//			}
//
//			@Override
//			protected ObjectObjectMap<?, ?> createCopy(Kryo kryo, ObjectObjectMap<?, ?> original) {
//				return new ObjectObjectMap<>(original.size());
//			}
//		});

//		kryo.register(Vector2.class);
//		kryo.register(ObjectList.class, new CollectionSerializer<ObjectList<Vector2>>(){
//			@Override
//			protected ObjectList<Vector2> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectList<>(size);
//			}
//		});
//		kryo.register(ObjectObjectMap.class, new MapSerializer<ObjectObjectMap<String, ObjectList<Vector2>>>(){
//			@Override
//			protected ObjectObjectMap<String, ObjectList<Vector2>> create(Kryo kryo, Input input, Class type, int size) {
//				return new ObjectObjectMap<>((int)(size / Utilities.getDefaultLoadFactor()+1), Utilities.getDefaultLoadFactor());
//			}
//		});

//		Kryo kryo = new Kryo();
//		kryo.register(Vector2.class);
//		CollectionSerializer<ObjectList<Vector2>> cs = new CollectionSerializer<>();
//		cs.setElementClass(Vector2.class);
//		kryo.register(ObjectList.class, cs);
//		MapSerializer<ObjectObjectMap<String, ObjectList<Vector2>>> ms = new MapSerializer<>();
//		ms.setKeyClass(String.class);
//		ms.setValueClass(ObjectList.class, cs);
//		kryo.register(ObjectObjectMap.class, ms);

		try {
			Output output = new Output(new FileOutputStream("kryomore.dat"));
			kryo.writeObject(output, big);
			output.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}

// TEMPLATE
/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 22:
 * <br>
 *
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 *
 */

//OLD
/*
 * Windows 10, 10th gen i7 mobile hexacore at 2.6 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoMoreWriteBench score: 421.100128 (421.1 604.3%)
 *                uncertainty:   1.5%
 * <br>
 * OpenJ9 Java 15:
 * <br>
 * KryoMoreWriteBench score: 349.401306 (349.4 585.6%)
 *                uncertainty:   2.5%
 * <br>
 * HotSpot Java 16 (AdoptOpenJDK):
 * <br>
 * KryoMoreWriteBench score: 433.209595 (433.2 607.1%)
 *                uncertainty:   1.9%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoMoreWriteBench score: 428.985321 (429.0 606.1%)
 *                uncertainty:   2.0%
 * <br>
 * GraalVM Java 17:
 * <br>
 * KryoMoreWriteBench score: 441.461853 (441.5 609.0%)
 *                uncertainty:   2.1%
 * <br>
 * OpenJ9 Java 17 (Semeru):
 * <br>
 *
 * <br>
 * HotSpot Java 18 (Adoptium):
 * <br>
 * KryoMoreWriteBench score: 389.832520 (389.8 596.6%)
 *                uncertainty:   7.9%
 * <br>
 * HotSpot Java 19 (BellSoft):
 * <br>
 * KryoMoreWriteBench score: 410.989960 (411.0 601.9%)
 *                uncertainty:   2.2%
 */
