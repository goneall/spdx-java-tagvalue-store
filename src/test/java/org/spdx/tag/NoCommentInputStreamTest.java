/**
 * SPDX-FileContributor: Gary O'Neall
 * SPDX-FileCopyrightText: Copyright (c) 2013 Source Auditor Inc.
 * SPDX-FileType: SOURCE
 * SPDX-License-Identifier: Apache-2.0
 * <p>
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * <p>
 *       https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.spdx.tag;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class NoCommentInputStreamTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.spdx.tag.NoCommentInputStream#read()}.
	 * @throws IOException
	 */
	public void testRead() throws IOException {
		String testString = "Now is the time\nfor all good\nmen\nto\tcome to the aid of thier country";
		byte[] testBytes = testString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		for (int i = 0; i < testBytes.length; i++) {
			assertEquals(testBytes[i], nci.read());
		}
		assertEquals(-1, nci.read());
		nci.close();
	}

	public void testReadWithComment() throws IOException {
		String inputString = "Now is #the time\n#for all good\nmen\rto\tcome to the aid of thier country";
		String testString = "Now is #the time\nmen\nto\tcome to the aid of thier country";
		byte[] testBytes = testString.getBytes();
		byte[] testInputBytes = inputString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testInputBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		for (int i = 0; i < testBytes.length; i++) {
			assertEquals(testBytes[i], nci.read());
		}
		assertEquals(-1, nci.read());
		nci.close();
	}

	public void testReadWithTextTags() throws IOException {
		String inputString = "Now is the time\n#for all good\nmen\rto\tcome <text>to \n#the aid of </text>\nthier country\n#also";
		String testString = "Now is the time\nmen\nto\tcome <text>to \n#the aid of </text>\nthier country";;
		byte[] testBytes = testString.getBytes();
		byte[] testInputBytes = inputString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testInputBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		for (int i = 0; i < testBytes.length; i++) {
			assertEquals(testBytes[i], nci.read());
		}
		assertEquals(-1, nci.read());
		nci.close();
	}

	/**
	 * Test method for {@link java.io.InputStream#read(byte[], int, int)}.
	 * @throws IOException
	 */
	public void testReadByteArrayIntInt() throws IOException {
		String testString = "Now is the time\nfor all good\nmen\nto\tcome to the aid of thier country";
		byte[] testBytes = testString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		byte[] result = new byte[testBytes.length];
		int bytesRead = nci.read(result, 0, result.length);
		assertEquals(testBytes.length, bytesRead);
		for (int i = 0; i < testBytes.length; i++) {
			assertEquals(testBytes[i], result[i]);
		}
		assertEquals(-1, nci.read());
		nci.close();
	}

	/**
	 * Test method for {@link java.io.InputStream#skip(long)}.
	 * @throws IOException
	 */
	public void testSkip() throws IOException {
		String testString = "Now is the time\nfor all good\nmen\nto\tcome to the aid of thier country";
		byte[] testBytes = testString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		nci.skip(5);
		for (int i = 5; i < testBytes.length; i++) {
			assertEquals(testBytes[i], nci.read());
		}
		assertEquals(-1, nci.read());
		nci.close();
	}

	/**
	 * Test method for {@link java.io.InputStream#markSupported()}.
	 * @throws IOException
	 */
	public void testMarkSupported() throws IOException {
		String testString = "Now is the time\nfor all good\nmen\nto\tcome to the aid of thier country";
		byte[] testBytes = testString.getBytes();
		ByteArrayInputStream input = new ByteArrayInputStream(testBytes);
		NoCommentInputStream nci = new NoCommentInputStream(input);
		assertFalse(nci.markSupported());
		nci.close();
	}
}
