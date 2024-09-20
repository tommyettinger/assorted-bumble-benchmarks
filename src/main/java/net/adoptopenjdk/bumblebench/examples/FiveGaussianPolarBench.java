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

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * FiveGaussianPolarBench score: 10387409.000000 (10.39M 1615.6%)
 *                    uncertainty:   0.8%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FiveGaussianPolarBench score: 11262775.000000 (11.26M 1623.7%)
 *                    uncertainty:   0.6%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FiveGaussianPolarBench score: 11604896.000000 (11.60M 1626.7%)
 *                    uncertainty:   0.4%
 * <br>
 * GraalVM Java 22:
 * <br>
 * FiveGaussianPolarBench score: 28842328.000000 (28.84M 1717.7%)
 *                    uncertainty:   1.0%
 */
public final class FiveGaussianPolarBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
        PouchRandom rng = new PouchRandom(0x12345678);
        double sum = 0.0;
        for (long i = 0; i < numIterations; i++) {
            double v1, v2, s, multiplier;
            do {
                v1 = 2 * rng.nextDouble() - 1;   // between -1.0 and 1.0
                v2 = 2 * rng.nextDouble() - 1;   // between -1.0 and 1.0
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            multiplier = Math.sqrt(-2 * Math.log(s) / s);
            sum += v1 * multiplier;
            sum += v2 * multiplier;
            do {
                v1 = 2 * rng.nextDouble() - 1;   // between -1.0 and 1.0
                v2 = 2 * rng.nextDouble() - 1;   // between -1.0 and 1.0
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            multiplier = Math.sqrt(-2 * Math.log(s) / s);
            sum += v1 * multiplier;
            sum += v2 * multiplier;
            do {
                v1 = 2 * rng.nextDouble() - 1;   // between -1.0 and 1.0
                v2 = rng.nextDouble();   // between 0.0 and 1.0
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            multiplier = Math.sqrt(-2 * Math.log(s) / s);
            sum += v1 * multiplier;
        }
		return numIterations;
	}
}
