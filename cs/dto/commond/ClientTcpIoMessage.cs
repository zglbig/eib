using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class ClientTcpIoMessage : SerializeMessage {
	[ProtoMember(1)]
	public string interfaceName;
	[ProtoMember(2)]
	public string methodName;
	[ProtoMember(3)]
	public string args;
}
