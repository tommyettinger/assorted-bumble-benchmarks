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

import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Quaternion;
import com.github.yellowstonegames.grid.Point4Float;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.apache.fury.logging.LoggerFactory;
import org.apache.fury.serializer.collection.CollectionSerializers;

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
 * FuryQuatReadBench score: 71.158875 (71.16 426.5%)
 *               uncertainty:   4.6%
 */
public final class FuryQuatReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		byte[] data = new HeadlessFiles().local("furyQuat.dat").readBytes();
		ArrayList<Quaternion> pts;
		LoggerFactory.disableLogging();
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		fury.registerSerializer(ArrayList.class, new CollectionSerializers.ArrayListSerializer(fury));
		fury.register(Quaternion.class);

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				pts = fury.deserializeJavaObject(data, ArrayList.class);
				counter += pts.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}