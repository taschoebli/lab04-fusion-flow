package io.flowing.retail.reporting.Serialization.model; /**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** AVRO schema for Booking data records. */
@org.apache.avro.specific.AvroGenerated
public class AnonymizedBookingEntry extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
    private static final long serialVersionUID = -3936060008933351592L;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry\",\"doc\":\"AVRO schema for Booking data records.\",\"fields\":[{\"name\":\"locationId\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"bookingKey\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"productName\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"anonymizedCustomer\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"bookingDateTime\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"eventDateTime\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"amount\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"paymentStatusIsPaid\",\"type\":[\"null\",\"boolean\"],\"default\":null},{\"name\":\"timestamp\",\"type\":[\"null\",\"string\"],\"default\":null}]}");
    public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

    private static SpecificData MODEL$ = new SpecificData();

    private static final BinaryMessageEncoder<AnonymizedBookingEntry> ENCODER =
            new BinaryMessageEncoder<AnonymizedBookingEntry>(MODEL$, SCHEMA$);

    private static final BinaryMessageDecoder<AnonymizedBookingEntry> DECODER =
            new BinaryMessageDecoder<AnonymizedBookingEntry>(MODEL$, SCHEMA$);

    /**
     * Return the BinaryMessageDecoder instance used by this class.
     */
    public static BinaryMessageDecoder<AnonymizedBookingEntry> getDecoder() {
        return DECODER;
    }

    /**
     * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
     * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
     */
    public static BinaryMessageDecoder<AnonymizedBookingEntry> createDecoder(SchemaStore resolver) {
        return new BinaryMessageDecoder<AnonymizedBookingEntry>(MODEL$, SCHEMA$, resolver);
    }

    /** Serializes this io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry to a ByteBuffer. */
    public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
        return ENCODER.encode(this);
    }

    /** Deserializes a io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry from a ByteBuffer. */
    public static AnonymizedBookingEntry fromByteBuffer(
            java.nio.ByteBuffer b) throws java.io.IOException {
        return DECODER.decode(b);
    }

    @Deprecated public java.lang.Integer locationId;
    @Deprecated public java.lang.CharSequence bookingKey;
    @Deprecated public java.lang.CharSequence productName;
    @Deprecated public java.lang.Integer anonymizedCustomer;
    @Deprecated public java.lang.CharSequence bookingDateTime;
    @Deprecated public java.lang.CharSequence eventDateTime;
    @Deprecated public java.lang.Integer amount;
    @Deprecated public java.lang.Boolean paymentStatusIsPaid;
    @Deprecated public java.lang.CharSequence timestamp;

    /**
     * Default constructor.  Note that this does not initialize fields
     * to their default values from the schema.  If that is desired then
     * one should use <code>newBuilder()</code>.
     */
    public AnonymizedBookingEntry() {}

    /**
     * All-args constructor.
     * @param locationId The new value for locationId
     * @param bookingKey The new value for bookingKey
     * @param productName The new value for productName
     * @param anonymizedCustomer The new value for anonymizedCustomer
     * @param bookingDateTime The new value for bookingDateTime
     * @param eventDateTime The new value for eventDateTime
     * @param amount The new value for amount
     * @param paymentStatusIsPaid The new value for paymentStatusIsPaid
     * @param timestamp The new value for timestamp
     */
    public AnonymizedBookingEntry(java.lang.Integer locationId, java.lang.CharSequence bookingKey, java.lang.CharSequence productName, java.lang.Integer anonymizedCustomer, java.lang.CharSequence bookingDateTime, java.lang.CharSequence eventDateTime, java.lang.Integer amount, java.lang.Boolean paymentStatusIsPaid, java.lang.CharSequence timestamp) {
        this.locationId = locationId;
        this.bookingKey = bookingKey;
        this.productName = productName;
        this.anonymizedCustomer = anonymizedCustomer;
        this.bookingDateTime = bookingDateTime;
        this.eventDateTime = eventDateTime;
        this.amount = amount;
        this.paymentStatusIsPaid = paymentStatusIsPaid;
        this.timestamp = timestamp;
    }

    public org.apache.avro.Schema getSchema() { return SCHEMA$; }
    // Used by DatumWriter.  Applications should not call.
    public java.lang.Object get(int field$) {
        switch (field$) {
            case 0: return locationId;
            case 1: return bookingKey;
            case 2: return productName;
            case 3: return anonymizedCustomer;
            case 4: return bookingDateTime;
            case 5: return eventDateTime;
            case 6: return amount;
            case 7: return paymentStatusIsPaid;
            case 8: return timestamp;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    // Used by DatumReader.  Applications should not call.
    @SuppressWarnings(value="unchecked")
    public void put(int field$, java.lang.Object value$) {
        switch (field$) {
            case 0: locationId = (java.lang.Integer)value$; break;
            case 1: bookingKey = (java.lang.CharSequence)value$; break;
            case 2: productName = (java.lang.CharSequence)value$; break;
            case 3: anonymizedCustomer = (java.lang.Integer)value$; break;
            case 4: bookingDateTime = (java.lang.CharSequence)value$; break;
            case 5: eventDateTime = (java.lang.CharSequence)value$; break;
            case 6: amount = (java.lang.Integer)value$; break;
            case 7: paymentStatusIsPaid = (java.lang.Boolean)value$; break;
            case 8: timestamp = (java.lang.CharSequence)value$; break;
            default: throw new org.apache.avro.AvroRuntimeException("Bad index");
        }
    }

    /**
     * Gets the value of the 'locationId' field.
     * @return The value of the 'locationId' field.
     */
    public java.lang.Integer getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the 'locationId' field.
     * @param value the value to set.
     */
    public void setLocationId(java.lang.Integer value) {
        this.locationId = value;
    }

    /**
     * Gets the value of the 'bookingKey' field.
     * @return The value of the 'bookingKey' field.
     */
    public java.lang.CharSequence getBookingKey() {
        return bookingKey;
    }

    /**
     * Sets the value of the 'bookingKey' field.
     * @param value the value to set.
     */
    public void setBookingKey(java.lang.CharSequence value) {
        this.bookingKey = value;
    }

    /**
     * Gets the value of the 'productName' field.
     * @return The value of the 'productName' field.
     */
    public java.lang.CharSequence getProductName() {
        return productName;
    }

    /**
     * Sets the value of the 'productName' field.
     * @param value the value to set.
     */
    public void setProductName(java.lang.CharSequence value) {
        this.productName = value;
    }

    /**
     * Gets the value of the 'anonymizedCustomer' field.
     * @return The value of the 'anonymizedCustomer' field.
     */
    public java.lang.Integer getAnonymizedCustomer() {
        return anonymizedCustomer;
    }

    /**
     * Sets the value of the 'anonymizedCustomer' field.
     * @param value the value to set.
     */
    public void setAnonymizedCustomer(java.lang.Integer value) {
        this.anonymizedCustomer = value;
    }

    /**
     * Gets the value of the 'bookingDateTime' field.
     * @return The value of the 'bookingDateTime' field.
     */
    public java.lang.CharSequence getBookingDateTime() {
        return bookingDateTime;
    }

    /**
     * Sets the value of the 'bookingDateTime' field.
     * @param value the value to set.
     */
    public void setBookingDateTime(java.lang.CharSequence value) {
        this.bookingDateTime = value;
    }

    /**
     * Gets the value of the 'eventDateTime' field.
     * @return The value of the 'eventDateTime' field.
     */
    public java.lang.CharSequence getEventDateTime() {
        return eventDateTime;
    }

    /**
     * Sets the value of the 'eventDateTime' field.
     * @param value the value to set.
     */
    public void setEventDateTime(java.lang.CharSequence value) {
        this.eventDateTime = value;
    }

    /**
     * Gets the value of the 'amount' field.
     * @return The value of the 'amount' field.
     */
    public java.lang.Integer getAmount() {
        return amount;
    }

    /**
     * Sets the value of the 'amount' field.
     * @param value the value to set.
     */
    public void setAmount(java.lang.Integer value) {
        this.amount = value;
    }

    /**
     * Gets the value of the 'paymentStatusIsPaid' field.
     * @return The value of the 'paymentStatusIsPaid' field.
     */
    public java.lang.Boolean getPaymentStatusIsPaid() {
        return paymentStatusIsPaid;
    }

    /**
     * Sets the value of the 'paymentStatusIsPaid' field.
     * @param value the value to set.
     */
    public void setPaymentStatusIsPaid(java.lang.Boolean value) {
        this.paymentStatusIsPaid = value;
    }

    /**
     * Gets the value of the 'timestamp' field.
     * @return The value of the 'timestamp' field.
     */
    public java.lang.CharSequence getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the 'timestamp' field.
     * @param value the value to set.
     */
    public void setTimestamp(java.lang.CharSequence value) {
        this.timestamp = value;
    }

    /**
     * Creates a new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder.
     * @return A new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder
     */
    public static AnonymizedBookingEntry.Builder newBuilder() {
        return new AnonymizedBookingEntry.Builder();
    }

    /**
     * Creates a new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder by copying an existing Builder.
     * @param other The existing builder to copy.
     * @return A new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder
     */
    public static AnonymizedBookingEntry.Builder newBuilder(AnonymizedBookingEntry.Builder other) {
        return new AnonymizedBookingEntry.Builder(other);
    }

    /**
     * Creates a new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder by copying an existing io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry instance.
     * @param other The existing instance to copy.
     * @return A new io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry RecordBuilder
     */
    public static AnonymizedBookingEntry.Builder newBuilder(AnonymizedBookingEntry other) {
        return new AnonymizedBookingEntry.Builder(other);
    }

    /**
     * RecordBuilder for io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry instances.
     */
    public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AnonymizedBookingEntry>
            implements org.apache.avro.data.RecordBuilder<AnonymizedBookingEntry> {

        private java.lang.Integer locationId;
        private java.lang.CharSequence bookingKey;
        private java.lang.CharSequence productName;
        private java.lang.Integer anonymizedCustomer;
        private java.lang.CharSequence bookingDateTime;
        private java.lang.CharSequence eventDateTime;
        private java.lang.Integer amount;
        private java.lang.Boolean paymentStatusIsPaid;
        private java.lang.CharSequence timestamp;

        /** Creates a new Builder */
        private Builder() {
            super(SCHEMA$);
        }

        /**
         * Creates a Builder by copying an existing Builder.
         * @param other The existing Builder to copy.
         */
        private Builder(AnonymizedBookingEntry.Builder other) {
            super(other);
            if (isValidValue(fields()[0], other.locationId)) {
                this.locationId = data().deepCopy(fields()[0].schema(), other.locationId);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.bookingKey)) {
                this.bookingKey = data().deepCopy(fields()[1].schema(), other.bookingKey);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.productName)) {
                this.productName = data().deepCopy(fields()[2].schema(), other.productName);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.anonymizedCustomer)) {
                this.anonymizedCustomer = data().deepCopy(fields()[3].schema(), other.anonymizedCustomer);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.bookingDateTime)) {
                this.bookingDateTime = data().deepCopy(fields()[4].schema(), other.bookingDateTime);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.eventDateTime)) {
                this.eventDateTime = data().deepCopy(fields()[5].schema(), other.eventDateTime);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.amount)) {
                this.amount = data().deepCopy(fields()[6].schema(), other.amount);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.paymentStatusIsPaid)) {
                this.paymentStatusIsPaid = data().deepCopy(fields()[7].schema(), other.paymentStatusIsPaid);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[8].schema(), other.timestamp);
                fieldSetFlags()[8] = true;
            }
        }

        /**
         * Creates a Builder by copying an existing io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry instance
         * @param other The existing instance to copy.
         */
        private Builder(AnonymizedBookingEntry other) {
            super(SCHEMA$);
            if (isValidValue(fields()[0], other.locationId)) {
                this.locationId = data().deepCopy(fields()[0].schema(), other.locationId);
                fieldSetFlags()[0] = true;
            }
            if (isValidValue(fields()[1], other.bookingKey)) {
                this.bookingKey = data().deepCopy(fields()[1].schema(), other.bookingKey);
                fieldSetFlags()[1] = true;
            }
            if (isValidValue(fields()[2], other.productName)) {
                this.productName = data().deepCopy(fields()[2].schema(), other.productName);
                fieldSetFlags()[2] = true;
            }
            if (isValidValue(fields()[3], other.anonymizedCustomer)) {
                this.anonymizedCustomer = data().deepCopy(fields()[3].schema(), other.anonymizedCustomer);
                fieldSetFlags()[3] = true;
            }
            if (isValidValue(fields()[4], other.bookingDateTime)) {
                this.bookingDateTime = data().deepCopy(fields()[4].schema(), other.bookingDateTime);
                fieldSetFlags()[4] = true;
            }
            if (isValidValue(fields()[5], other.eventDateTime)) {
                this.eventDateTime = data().deepCopy(fields()[5].schema(), other.eventDateTime);
                fieldSetFlags()[5] = true;
            }
            if (isValidValue(fields()[6], other.amount)) {
                this.amount = data().deepCopy(fields()[6].schema(), other.amount);
                fieldSetFlags()[6] = true;
            }
            if (isValidValue(fields()[7], other.paymentStatusIsPaid)) {
                this.paymentStatusIsPaid = data().deepCopy(fields()[7].schema(), other.paymentStatusIsPaid);
                fieldSetFlags()[7] = true;
            }
            if (isValidValue(fields()[8], other.timestamp)) {
                this.timestamp = data().deepCopy(fields()[8].schema(), other.timestamp);
                fieldSetFlags()[8] = true;
            }
        }

        /**
         * Gets the value of the 'locationId' field.
         * @return The value.
         */
        public java.lang.Integer getLocationId() {
            return locationId;
        }

        /**
         * Sets the value of the 'locationId' field.
         * @param value The value of 'locationId'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setLocationId(java.lang.Integer value) {
            validate(fields()[0], value);
            this.locationId = value;
            fieldSetFlags()[0] = true;
            return this;
        }

        /**
         * Checks whether the 'locationId' field has been set.
         * @return True if the 'locationId' field has been set, false otherwise.
         */
        public boolean hasLocationId() {
            return fieldSetFlags()[0];
        }


        /**
         * Clears the value of the 'locationId' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearLocationId() {
            locationId = null;
            fieldSetFlags()[0] = false;
            return this;
        }

        /**
         * Gets the value of the 'bookingKey' field.
         * @return The value.
         */
        public java.lang.CharSequence getBookingKey() {
            return bookingKey;
        }

        /**
         * Sets the value of the 'bookingKey' field.
         * @param value The value of 'bookingKey'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setBookingKey(java.lang.CharSequence value) {
            validate(fields()[1], value);
            this.bookingKey = value;
            fieldSetFlags()[1] = true;
            return this;
        }

        /**
         * Checks whether the 'bookingKey' field has been set.
         * @return True if the 'bookingKey' field has been set, false otherwise.
         */
        public boolean hasBookingKey() {
            return fieldSetFlags()[1];
        }


        /**
         * Clears the value of the 'bookingKey' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearBookingKey() {
            bookingKey = null;
            fieldSetFlags()[1] = false;
            return this;
        }

        /**
         * Gets the value of the 'productName' field.
         * @return The value.
         */
        public java.lang.CharSequence getProductName() {
            return productName;
        }

        /**
         * Sets the value of the 'productName' field.
         * @param value The value of 'productName'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setProductName(java.lang.CharSequence value) {
            validate(fields()[2], value);
            this.productName = value;
            fieldSetFlags()[2] = true;
            return this;
        }

        /**
         * Checks whether the 'productName' field has been set.
         * @return True if the 'productName' field has been set, false otherwise.
         */
        public boolean hasProductName() {
            return fieldSetFlags()[2];
        }


        /**
         * Clears the value of the 'productName' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearProductName() {
            productName = null;
            fieldSetFlags()[2] = false;
            return this;
        }

        /**
         * Gets the value of the 'anonymizedCustomer' field.
         * @return The value.
         */
        public java.lang.Integer getAnonymizedCustomer() {
            return anonymizedCustomer;
        }

        /**
         * Sets the value of the 'anonymizedCustomer' field.
         * @param value The value of 'anonymizedCustomer'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setAnonymizedCustomer(java.lang.Integer value) {
            validate(fields()[3], value);
            this.anonymizedCustomer = value;
            fieldSetFlags()[3] = true;
            return this;
        }

        /**
         * Checks whether the 'anonymizedCustomer' field has been set.
         * @return True if the 'anonymizedCustomer' field has been set, false otherwise.
         */
        public boolean hasAnonymizedCustomer() {
            return fieldSetFlags()[3];
        }


        /**
         * Clears the value of the 'anonymizedCustomer' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearAnonymizedCustomer() {
            anonymizedCustomer = null;
            fieldSetFlags()[3] = false;
            return this;
        }

        /**
         * Gets the value of the 'bookingDateTime' field.
         * @return The value.
         */
        public java.lang.CharSequence getBookingDateTime() {
            return bookingDateTime;
        }

        /**
         * Sets the value of the 'bookingDateTime' field.
         * @param value The value of 'bookingDateTime'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setBookingDateTime(java.lang.CharSequence value) {
            validate(fields()[4], value);
            this.bookingDateTime = value;
            fieldSetFlags()[4] = true;
            return this;
        }

        /**
         * Checks whether the 'bookingDateTime' field has been set.
         * @return True if the 'bookingDateTime' field has been set, false otherwise.
         */
        public boolean hasBookingDateTime() {
            return fieldSetFlags()[4];
        }


        /**
         * Clears the value of the 'bookingDateTime' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearBookingDateTime() {
            bookingDateTime = null;
            fieldSetFlags()[4] = false;
            return this;
        }

        /**
         * Gets the value of the 'eventDateTime' field.
         * @return The value.
         */
        public java.lang.CharSequence getEventDateTime() {
            return eventDateTime;
        }

        /**
         * Sets the value of the 'eventDateTime' field.
         * @param value The value of 'eventDateTime'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setEventDateTime(java.lang.CharSequence value) {
            validate(fields()[5], value);
            this.eventDateTime = value;
            fieldSetFlags()[5] = true;
            return this;
        }

        /**
         * Checks whether the 'eventDateTime' field has been set.
         * @return True if the 'eventDateTime' field has been set, false otherwise.
         */
        public boolean hasEventDateTime() {
            return fieldSetFlags()[5];
        }


        /**
         * Clears the value of the 'eventDateTime' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearEventDateTime() {
            eventDateTime = null;
            fieldSetFlags()[5] = false;
            return this;
        }

        /**
         * Gets the value of the 'amount' field.
         * @return The value.
         */
        public java.lang.Integer getAmount() {
            return amount;
        }

        /**
         * Sets the value of the 'amount' field.
         * @param value The value of 'amount'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setAmount(java.lang.Integer value) {
            validate(fields()[6], value);
            this.amount = value;
            fieldSetFlags()[6] = true;
            return this;
        }

        /**
         * Checks whether the 'amount' field has been set.
         * @return True if the 'amount' field has been set, false otherwise.
         */
        public boolean hasAmount() {
            return fieldSetFlags()[6];
        }


        /**
         * Clears the value of the 'amount' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearAmount() {
            amount = null;
            fieldSetFlags()[6] = false;
            return this;
        }

        /**
         * Gets the value of the 'paymentStatusIsPaid' field.
         * @return The value.
         */
        public java.lang.Boolean getPaymentStatusIsPaid() {
            return paymentStatusIsPaid;
        }

        /**
         * Sets the value of the 'paymentStatusIsPaid' field.
         * @param value The value of 'paymentStatusIsPaid'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setPaymentStatusIsPaid(java.lang.Boolean value) {
            validate(fields()[7], value);
            this.paymentStatusIsPaid = value;
            fieldSetFlags()[7] = true;
            return this;
        }

        /**
         * Checks whether the 'paymentStatusIsPaid' field has been set.
         * @return True if the 'paymentStatusIsPaid' field has been set, false otherwise.
         */
        public boolean hasPaymentStatusIsPaid() {
            return fieldSetFlags()[7];
        }


        /**
         * Clears the value of the 'paymentStatusIsPaid' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearPaymentStatusIsPaid() {
            paymentStatusIsPaid = null;
            fieldSetFlags()[7] = false;
            return this;
        }

        /**
         * Gets the value of the 'timestamp' field.
         * @return The value.
         */
        public java.lang.CharSequence getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the 'timestamp' field.
         * @param value The value of 'timestamp'.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder setTimestamp(java.lang.CharSequence value) {
            validate(fields()[8], value);
            this.timestamp = value;
            fieldSetFlags()[8] = true;
            return this;
        }

        /**
         * Checks whether the 'timestamp' field has been set.
         * @return True if the 'timestamp' field has been set, false otherwise.
         */
        public boolean hasTimestamp() {
            return fieldSetFlags()[8];
        }


        /**
         * Clears the value of the 'timestamp' field.
         * @return This builder.
         */
        public AnonymizedBookingEntry.Builder clearTimestamp() {
            timestamp = null;
            fieldSetFlags()[8] = false;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public AnonymizedBookingEntry build() {
            try {
                AnonymizedBookingEntry record = new AnonymizedBookingEntry();
                record.locationId = fieldSetFlags()[0] ? this.locationId : (java.lang.Integer) defaultValue(fields()[0]);
                record.bookingKey = fieldSetFlags()[1] ? this.bookingKey : (java.lang.CharSequence) defaultValue(fields()[1]);
                record.productName = fieldSetFlags()[2] ? this.productName : (java.lang.CharSequence) defaultValue(fields()[2]);
                record.anonymizedCustomer = fieldSetFlags()[3] ? this.anonymizedCustomer : (java.lang.Integer) defaultValue(fields()[3]);
                record.bookingDateTime = fieldSetFlags()[4] ? this.bookingDateTime : (java.lang.CharSequence) defaultValue(fields()[4]);
                record.eventDateTime = fieldSetFlags()[5] ? this.eventDateTime : (java.lang.CharSequence) defaultValue(fields()[5]);
                record.amount = fieldSetFlags()[6] ? this.amount : (java.lang.Integer) defaultValue(fields()[6]);
                record.paymentStatusIsPaid = fieldSetFlags()[7] ? this.paymentStatusIsPaid : (java.lang.Boolean) defaultValue(fields()[7]);
                record.timestamp = fieldSetFlags()[8] ? this.timestamp : (java.lang.CharSequence) defaultValue(fields()[8]);
                return record;
            } catch (java.lang.Exception e) {
                throw new org.apache.avro.AvroRuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumWriter<AnonymizedBookingEntry>
            WRITER$ = (org.apache.avro.io.DatumWriter<AnonymizedBookingEntry>)MODEL$.createDatumWriter(SCHEMA$);

    @Override public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        WRITER$.write(this, SpecificData.getEncoder(out));
    }

    @SuppressWarnings("unchecked")
    private static final org.apache.avro.io.DatumReader<AnonymizedBookingEntry>
            READER$ = (org.apache.avro.io.DatumReader<AnonymizedBookingEntry>)MODEL$.createDatumReader(SCHEMA$);

    @Override public void readExternal(java.io.ObjectInput in)
            throws java.io.IOException {
        READER$.read(this, SpecificData.getDecoder(in));
    }

}
