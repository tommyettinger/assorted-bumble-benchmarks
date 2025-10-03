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

import com.github.tommyettinger.random.GoldenQuasiRandom;
import com.github.tommyettinger.random.LFSR64QuasiRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * GoldenQuasiRandomBench score: 2353903360.000000 (2.354G 2157.9%)
 *                    uncertainty:   0.1%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * GoldenQuasiRandomBench score: 4404090368.000000 (4.404G 2220.6%)
 *                    uncertainty:   1.8%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * GoldenQuasiRandomBench score: 4439708160.000000 (4.440G 2221.4%)
 *                    uncertainty:   0.3%
 * <br>
 * GraalVM Java 24:
 * <br>
 * GoldenQuasiRandomBench score: 18640728064.000000 (18.64G 2364.9%)
 *                    uncertainty:   0.2%
 */
public final class GoldenQuasiRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
        GoldenQuasiRandom rng = new GoldenQuasiRandom(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
