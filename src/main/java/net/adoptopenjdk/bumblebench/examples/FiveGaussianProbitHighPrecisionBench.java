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
import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianProbitHighPrecisionBench score: 2239131.750000 (2.239M 1462.2%)
 *                                  uncertainty:   0.7%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianProbitHighPrecisionBench score: 4160924.000000 (4.161M 1524.1%)
 *                                  uncertainty:   2.0%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianProbitHighPrecisionBench score: 3826475.000000 (3.826M 1515.7%)
 *                                  uncertainty:   0.4%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianProbitHighPrecisionBench score: 6480846.000000 (6.481M 1568.4%)
 *                                  uncertainty:   1.0%
 */
public final class FiveGaussianProbitHighPrecisionBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		PouchRandom rng = new PouchRandom(0x12345678);
		double sum = 0.0;
		for (long i = 0; i < numIterations; i++) {
			sum += Borg.probitHighPrecision(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += Borg.probitHighPrecision(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += Borg.probitHighPrecision(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += Borg.probitHighPrecision(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
			sum += Borg.probitHighPrecision(((rng.nextLong() >>> 11) + 1L) * 1.1102230246251564E-16);
		}
		return numIterations;
	}
}
