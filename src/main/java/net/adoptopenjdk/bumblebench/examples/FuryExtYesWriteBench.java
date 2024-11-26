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
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.random.FourWheelRandom;
import com.github.yellowstonegames.grid.Point4Float;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.apache.fury.logging.LoggerFactory;
import org.apache.fury.memory.MemoryBuffer;
import org.apache.fury.serializer.collection.CollectionSerializers;
import squidpony.StringKit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
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
 * FuryExtYesWriteBench score: 64.928062 (64.93 417.3%)
 *                  uncertainty:   1.9%
 */
public final class FuryExtYesWriteBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		PouchRandom random = new PouchRandom(12345);
		ArrayList<Point4Float> pts = new ArrayList<>((1<<20));
		for (int j = 0; j < (1 << 20); j++) {
			pts.add(new Point4Float(random.nextExclusiveSignedFloat(), random.nextExclusiveSignedFloat(),
					random.nextExclusiveSignedFloat(), random.nextExclusiveSignedFloat()));
		}
		LoggerFactory.disableLogging();
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		fury.registerSerializer(ArrayList.class, new CollectionSerializers.ArrayListSerializer(fury));
		fury.register(Point4Float.class);

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				MemoryBuffer mem = MemoryBuffer.newHeapBuffer(65536);
				startTimer();
				fury.serializeJavaObject(mem, pts);
				pauseTimer();
				counter += mem.size();
			}
		}
		return numLoops * numIterationsPerLoop;
	}

	public static void main(String[] args) {
		PouchRandom random = new PouchRandom(12345);
		ArrayList<Point4Float> pts = new ArrayList<>((1<<20));
		for (int j = 0; j < (1 << 20); j++) {
			pts.add(new Point4Float(random.nextExclusiveSignedFloat(), random.nextExclusiveSignedFloat(),
					random.nextExclusiveSignedFloat(), random.nextExclusiveSignedFloat()));
		}
		LoggerFactory.disableLogging();
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		fury.registerSerializer(ArrayList.class, new CollectionSerializers.ArrayListSerializer(fury));
		fury.register(Point4Float.class);

		System.out.println("There are " + pts.size() + " keys in the Map.");

		try {
			FileOutputStream stream = new FileOutputStream("furyExtYes.dat");
			byte[] bytes = fury.serializeJavaObject(pts);
			System.out.println("Fury serialized data is " + bytes.length + " bytes in size.");
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
