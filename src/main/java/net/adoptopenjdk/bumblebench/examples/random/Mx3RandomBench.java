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

package net.adoptopenjdk.bumblebench.examples.random;

import com.github.tommyettinger.random.Mx3Random;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * Mx3RandomBench score: 1178805888.000000 (1.179G 2088.8%)
 *            uncertainty:   0.4%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Mx3RandomBench score: 1331102336.000000 (1.331G 2100.9%)
 *            uncertainty:   1.5%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Mx3RandomBench score: 1349764992.000000 (1.350G 2102.3%)
 *            uncertainty:   0.4%
 * <br>
 * GraalVM Java 24:
 * <br>
 * Mx3RandomBench score: 1390036352.000000 (1.390G 2105.3%)
 *            uncertainty:   0.3%
 */
public final class Mx3RandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Mx3Random rng = new Mx3Random(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}

