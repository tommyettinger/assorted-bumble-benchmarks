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

import com.github.tommyettinger.random.EnhancedRandom;
import com.github.tommyettinger.random.GoldenQuasiRandom;
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianProbitBench score: 12224914.000000 (12.22M 1631.9%)
 *                     uncertainty:   1.1%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianProbitBench score: 12390087.000000 (12.39M 1633.2%)
 *                     uncertainty:   0.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianProbitBench score: 12320710.000000 (12.32M 1632.7%)
 *                     uncertainty:   0.6%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianProbitBench score: 53633812.000000 (53.63M 1779.8%)
 *                     uncertainty:   0.3%
 */
// OLD, uses PouchRandom
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianProbitBench score: 11818947.000000 (11.82M 1628.5%)
 *                     uncertainty:   0.7%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianProbitBench score: 12115728.000000 (12.12M 1631.0%)
 *                     uncertainty:   1.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianProbitBench score: 12011566.000000 (12.01M 1630.1%)
 *                     uncertainty:   1.0%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianProbitBench score: 48729500.000000 (48.73M 1770.2%)
 *                     uncertainty:   2.6%
 */
public final class FiveGaussianProbitBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		GoldenQuasiRandom rng = new GoldenQuasiRandom(0x12345678L);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += EnhancedRandom.probit(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += EnhancedRandom.probit(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += EnhancedRandom.probit(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += EnhancedRandom.probit(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += EnhancedRandom.probit(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
		}
		return numIterations;
	}
}
