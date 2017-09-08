/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2017 GwtMaterialDesign
 * %%
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
 * #L%
 */
package gwt.material.design.addins.client.signature;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.shared.HandlerRegistration;
import gwt.material.design.addins.client.MaterialAddins;
import gwt.material.design.addins.client.signature.events.BeginSignatureEvent;
import gwt.material.design.addins.client.signature.events.ClearSignatureEvent;
import gwt.material.design.addins.client.signature.events.EndSignatureEvent;
import gwt.material.design.addins.client.signature.events.HasSignatureHandlers;
import gwt.material.design.addins.client.signature.js.JsSignaturePadOptions;
import gwt.material.design.addins.client.signature.js.SignaturePad;
import gwt.material.design.client.MaterialDesignBase;
import gwt.material.design.client.base.MaterialWidget;

public class MaterialSignaturePad extends MaterialWidget implements HasSignatureHandlers {

    static {
        if (MaterialAddins.isDebug()) {
            MaterialDesignBase.injectDebugJs(MaterialSignaturePadDebugClientBundle.INSTANCE.signaturePadDebugJs());
        } else {
            MaterialDesignBase.injectJs(MaterialSignaturePadClientBundle.INSTANCE.signaPadJs());
        }
    }


    public MaterialSignaturePad() {
        super(Document.get().createCanvasElement(), "signature-pad");
    }

    private SignaturePad signaturePad;

    @Override
    protected void initialize() {
        JsSignaturePadOptions options = new JsSignaturePadOptions();
        options.onBegin = () -> BeginSignatureEvent.fire(this);
        options.onEnd = () -> EndSignatureEvent.fire(this);
        signaturePad = new SignaturePad(getElement(), options);
    }

    public void clear() {
        getSignaturePad().clear();
        ClearSignatureEvent.fire(this);
    }

    public SignaturePad getSignaturePad() {
        if (signaturePad == null) {
            GWT.log("Please initialize the signature pad component");
        }
        return signaturePad;
    }

    @Override
    public HandlerRegistration addClearSignatureHandler(ClearSignatureEvent.ClearSignatureHandler handler) {
        return addHandler(handler, ClearSignatureEvent.TYPE);
    }

    @Override
    public HandlerRegistration addBeginSignatureHandler(BeginSignatureEvent.BeginSignatureHandler handler) {
        return addHandler(handler, BeginSignatureEvent.TYPE);
    }

    @Override
    public HandlerRegistration addEndSignatureHandler(EndSignatureEvent.EndSignatureHandler handler) {
        return addHandler(handler, EndSignatureEvent.TYPE);
    }
}
