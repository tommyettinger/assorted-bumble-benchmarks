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
import com.github.tommyettinger.ds.ObjectDeque;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.ds.ObjectObjectMap;
import com.github.tommyettinger.ds.ObjectObjectOrderedMap;
import com.github.tommyettinger.random.FourWheelRandom;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectDequeSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectListSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectObjectMapSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectObjectOrderedMapSerializer;
import com.github.tommyettinger.tantrum.libgdx.Vector2Serializer;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fory.Fory;
import org.apache.fory.config.Language;
import org.apache.fory.logging.LoggerFactory;
import org.apache.fory.memory.MemoryBuffer;
import squidpony.StringKit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 23 (Adoptium), Fory 0.7.1:
 * <br>
 * ForyMoreOrderedWriteBench score: 677.433350 (677.4 651.8%)
 *                       uncertainty:   2.0%
 * <br>
 * HotSpot Java 23 (Adoptium), Fory 0.8.0:
 * <br>
 * ForyMoreOrderedWriteBench score: 647.977356 (648.0 647.4%)
 *                       uncertainty:   6.3%
 */
public final class ForyMoreOrderedWriteBench extends MiniBench {
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
		ObjectObjectOrderedMap<String, ObjectDeque<Vector2>> big = new ObjectObjectOrderedMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, ObjectDeque.with(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			));
		}
		LoggerFactory.disableLogging();
		Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
		fory.registerSerializer(ObjectObjectOrderedMap.class, new ObjectObjectOrderedMapSerializer(fory));
		fory.registerSerializer(ObjectDeque.class, new ObjectDequeSerializer(fory));
		fory.registerSerializer(Vector2.class, new Vector2Serializer(fory));

		long counter = 0;

		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				MemoryBuffer mem = MemoryBuffer.newHeapBuffer(65536);
				startTimer();
				fory.serializeJavaObject(mem, big);
				pauseTimer();
				counter += mem.size();
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
		ObjectObjectOrderedMap<String, ObjectDeque<Vector2>> big = new ObjectObjectOrderedMap<>(unique.size);
		FourWheelRandom random = new FourWheelRandom(12345);
		for(String u : unique){
			big.put(u, ObjectDeque.with(
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f),
					new Vector2(random.nextExclusiveFloat() - 0.5f, random.nextExclusiveFloat() - 0.5f)
			));
		}

		System.out.println("There are " + big.size() + " keys in the Map.");

		LoggerFactory.disableLogging();
		Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
		fory.registerSerializer(ObjectObjectOrderedMap.class, new ObjectObjectOrderedMapSerializer(fory));
		fory.registerSerializer(ObjectDeque.class, new ObjectDequeSerializer(fory));
		fory.registerSerializer(Vector2.class, new Vector2Serializer(fory));

		try {
			FileOutputStream stream = new FileOutputStream("forymoreordered.dat");
			byte[] bytes = fory.serializeJavaObject(big);
			System.out.println("Fory (Tantrum) serialized data (jdkgdxds ordered) is " + bytes.length + " bytes in size.");
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

//OLD
/*
 * With serializeJavaObject():
 * <br>
 * Java 17:
 * <br>
 * ForyWriteBench score: 526.263489 (526.3 626.6%)
 *            uncertainty:   8.4%
 * <br>
 * With serialize():
 * <br>
 * Java 17:
 * <br>
 * ForyWriteBench score: 521.859558 (521.9 625.7%)
 *            uncertainty:  19.0%
 */