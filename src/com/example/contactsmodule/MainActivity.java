package com.example.contactsmodule;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contacts.model.PeopleInfo;

public class MainActivity extends Activity {

	static final String EMPTY_SPACE = "";
	EditText name, tellNumber, phoneNumber, faxNumber, email, address, company;
	Button sendButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponent();
	}

	private void initComponent() {
		name = (EditText) findViewById(R.id.name);
		tellNumber = (EditText) findViewById(R.id.tell_number);
		phoneNumber = (EditText) findViewById(R.id.phone_number);
		faxNumber = (EditText) findViewById(R.id.fax_number);
		email = (EditText) findViewById(R.id.email);
		address = (EditText) findViewById(R.id.address);
		company = (EditText) findViewById(R.id.company);
		sendButton = (Button) findViewById(R.id.send_button);
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// send to contacts
				PeopleInfo info = new PeopleInfo(
						name.getText().toString(),
						tellNumber.getText().toString(),
						phoneNumber.getText().toString(),
						faxNumber.getText().toString(),
						email.getText().toString(),
						address.getText().toString(),
						company.getText().toString());
				doSave(info);
			}
		});
	}

	private void doSave(PeopleInfo info) {
		String displayName = info.getName();
		String tellNumber = info.getTellNumber();
		String phoneNumber = info.getPhoneNumber();
		String faxNumber = info.getFaxNumber();
		String email = info.getEmail();
		String address = info.getAddress();
		String company = info.getCompanyName();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		// ------------------------------------------------------ Names
		if (displayName != null || !displayName.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName)
					.build());
		}

		// ------------------------------------------------------ Mobile Number
		if (tellNumber != null || !tellNumber.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, tellNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
					.build());
		}

		// ------------------------------------------------------ Home Numbers
		if (phoneNumber != null || !phoneNumber.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
					.build());
		}
		
		// ------------------------------------------------------ Fax Numbers
		if (faxNumber != null || !faxNumber.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, faxNumber)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK)
					.build());
		}
		
		// ------------------------------------------------------ Address
		if (address != null || !address.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.StructuredPostal.DATA, address)
					.withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK)
					.build());
		}
		
		// ------------------------------------------------------ Email
		if (email != null || !email.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
					.build());
		}
		
		// ------------------------------------------------------ Organization
		if (company != null && !company.equals(EMPTY_SPACE)) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
					.withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
					.build());
		}

		// Asking the Contact provider to create a new contact
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OperationApplicationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
