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

package net.adoptopenjdk.bumblebench.examples.binary;

import com.github.yellowstonegames.grid.Point4Float;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import net.adoptopenjdk.bumblebench.examples.random.PouchRandom;
import org.apache.fory.Fory;
import org.apache.fory.config.Language;
import org.apache.fory.logging.LoggerFactory;
import org.apache.fory.memory.MemoryBuffer;
import org.apache.fory.serializer.collection.CollectionSerializers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


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
 * ForyExtYesWriteBench score: 64.928062 (64.93 417.3%)
 *                  uncertainty:   1.9%
 * <br>
 * HotSpot Java 24 (BellSoft), Fory 1.6.1:
 * <br>
 * ForyExtYesWriteBench score: 111.676643 (111.7 471.6%)
 *                  uncertainty:   5.8%
 */
public final class ForyExtYesWriteBench extends MiniBench {
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
		Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
		fory.registerSerializer(ArrayList.class, new CollectionSerializers.ArrayListSerializer(fory.getTypeResolver()));
		fory.register(Point4Float.class);

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				MemoryBuffer mem = MemoryBuffer.newHeapBuffer(65536);
				startTimer();
				fory.serialize(mem, pts);
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
		Fory fory = Fory.builder().withLanguage(Language.JAVA).build();
		fory.registerSerializer(ArrayList.class, new CollectionSerializers.ArrayListSerializer(fory.getTypeResolver()));
		fory.register(Point4Float.class);

		System.out.println("There are " + pts.size() + " keys in the Map.");

		try {
			FileOutputStream stream = new FileOutputStream("foryExtYes.dat");
			byte[] bytes = fory.serialize(pts);
			System.out.println("Fory serialized data is " + bytes.length + " bytes in size.");
			stream.write(bytes);
			stream.flush();
			stream.close();
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
