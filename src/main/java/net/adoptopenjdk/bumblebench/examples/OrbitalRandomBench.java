
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

import com.github.tommyettinger.random.OrbitalRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * OrbitalRandomBench score: 851518464.000000 (851.5M 2056.3%)
 *                uncertainty:   0.5%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * OrbitalRandomBench score: 963446592.000000 (963.4M 2068.6%)
 *                uncertainty:   0.5%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * OrbitalRandomBench score: 971221952.000000 (971.2M 2069.4%)
 *                uncertainty:   0.6%
 * <br>
 * GraalVM Java 24:
 * <br>
 * OrbitalRandomBench score: 1222619008.000000 (1.223G 2092.4%)
 *                uncertainty:   0.2%
 */
public final class OrbitalRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		OrbitalRandom rng = new OrbitalRandom(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}

// TEMPLATE
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
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
 * GraalVM Java 24:
 * <br>
 *
 */
