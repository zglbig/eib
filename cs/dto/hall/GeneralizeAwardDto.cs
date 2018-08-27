using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class GeneralizeAwardDto : SerializeMessage {
	[ProtoMember(1)]
	public long selfGetGold;
	[ProtoMember(2)]
	public long selfHoldGold;
	[ProtoMember(3)]
	public long otherGetGold;
	[ProtoMember(4)]
	public long otherHoldGold;
}
