/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.bp.chrono

import org.scalatest.FunSuite

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.Locale
import org.threeten.bp.AssertionsHelper
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.ResolverStyle
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.Temporal
import org.threeten.bp.temporal.TemporalAccessor
import org.threeten.bp.temporal.TemporalAdjuster
import org.threeten.bp.temporal.TemporalAmount
import org.threeten.bp.temporal.TemporalField
import org.threeten.bp.temporal.TemporalUnit
import org.threeten.bp.temporal.ValueRange

class TestChronoLocalDateTimeSerialization extends FunSuite with AssertionsHelper {
  val data_of_calendars: List[Chronology] = {
    List(
      (HijrahChronology.INSTANCE),
      (IsoChronology.INSTANCE),
      (JapaneseChronology.INSTANCE),
      (MinguoChronology.INSTANCE),
      (ThaiBuddhistChronology.INSTANCE))
  }

  test("test_ChronoLocalDateTimeSerialization") {
    data_of_calendars.foreach { chrono =>
      val ref: LocalDateTime = LocalDate.of(2000, 1, 5).atTime(12, 1, 2, 3)
      val orginal: ChronoLocalDateTime[_] = chrono.date(ref).atTime(ref.toLocalTime)
      val baos: ByteArrayOutputStream = new ByteArrayOutputStream
      val out: ObjectOutputStream = new ObjectOutputStream(baos)
      out.writeObject(orginal)
      out.close()
      val bais: ByteArrayInputStream = new ByteArrayInputStream(baos.toByteArray)
      val in: ObjectInputStream = new ObjectInputStream(bais)
      val ser: ChronoLocalDateTime[_] = in.readObject.asInstanceOf[ChronoLocalDateTime[_]]
      assertTrue(ser == orginal)
    }
  }
}
