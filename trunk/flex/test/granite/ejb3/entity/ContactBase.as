/**
 * Generated by Gas3 v2.1.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (Contact.as).
 */

package test.granite.ejb3.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class ContactBase extends AbstractEntity {

        private var _address:Address;
        private var _email:String;
        private var _fax:String;
        private var _mobile:String;
        private var _person:Person;
        private var _phone:String;

        public function set address(value:Address):void {
            _address = value;
        }
        public function get address():Address {
            return _address;
        }

        public function set email(value:String):void {
            _email = value;
        }
        public function get email():String {
            return _email;
        }

        public function set fax(value:String):void {
            _fax = value;
        }
        public function get fax():String {
            return _fax;
        }

        public function set mobile(value:String):void {
            _mobile = value;
        }
        public function get mobile():String {
            return _mobile;
        }

        public function set person(value:Person):void {
            _person = value;
        }
        public function get person():Person {
            return _person;
        }

        public function set phone(value:String):void {
            _phone = value;
        }
        public function get phone():String {
            return _phone;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            if (meta::isInitialized()) {
                _address = input.readObject() as Address;
                _email = input.readObject() as String;
                _fax = input.readObject() as String;
                _mobile = input.readObject() as String;
                _person = input.readObject() as Person;
                _phone = input.readObject() as String;
            }
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            if (meta::isInitialized()) {
                output.writeObject(_address);
                output.writeObject(_email);
                output.writeObject(_fax);
                output.writeObject(_mobile);
                output.writeObject(_person);
                output.writeObject(_phone);
            }
        }
    }
}