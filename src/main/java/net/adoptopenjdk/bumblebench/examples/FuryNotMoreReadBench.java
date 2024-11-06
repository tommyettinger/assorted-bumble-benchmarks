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
import com.badlogic.gdx.math.Vector2;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.ds.ObjectObjectMap;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectListSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectObjectMapSerializer;
import com.github.tommyettinger.tantrum.libgdx.Vector2Serializer;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.apache.fury.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

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
 * HotSpot Java 23 (Adoptium):
 * <br>
 * FuryNotMoreReadBench score: 1071.419922 (1071 697.7%)
 *                  uncertainty:   0.4%
 */
public final class FuryNotMoreReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		byte[] data = new HeadlessFiles().local("furynotmore.dat").readBytes();
		ObjectObjectMap<String, ObjectList<Vector2>> big;
		LoggerFactory.disableLogging();
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		fury.register(ObjectObjectMap.class);
		fury.register(ObjectList.class);
		fury.register(Vector2.class);

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				big = fury.deserializeJavaObject(data, ObjectObjectMap.class);
				counter += big.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}

// OLD
/*
 * With deserializeJavaObject():
 * <br>
 * Java 17:
 * <br>
 * FuryReadBench score: 640.986328 (641.0 646.3%)
 *           uncertainty:   1.2%
 * <br>
 * With deserialize():
 * <br>
 * Java 17:
 * <br>
 * FuryReadBench score: 632.360962 (632.4 644.9%)
 *           uncertainty:   2.1%
 */

